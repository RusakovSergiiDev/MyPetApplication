package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.repo.FirebaseRepo
import com.example.mypetapplication.repo.LoadStatus
import com.example.mypetapplication.utils.SimpleNavigationEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    // Internal param(s)
    private val firebaseRepo = FirebaseRepo
    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val isEnglishDataLoadedSourceFlow = MutableStateFlow(false)
    private val isSpanishDataLoadedSourceFlow = MutableStateFlow(false)
    private val authStatusSourceFlow =
        combine(
            isTimerFinishedSourceFlow,
            isAuthenticationCompletedSourceFlow,
            isEnglishDataLoadedSourceFlow,
            isSpanishDataLoadedSourceFlow
        ) { isTimerFinished, isAuthenticationCompleted, isEnglishDataLoaded, isSpanishDataLoaded ->
            AuthStatus(
                isTimerFinished,
                isAuthenticationCompleted,
                isEnglishDataLoaded && isSpanishDataLoaded,
            )
        }

    // Event(s)
    val navigateToAuthSelectionEvent = SimpleNavigationEvent()
    val navigateToHomeEvent = SimpleNavigationEvent()

    init {
        authStatusSourceFlow
            .onEach { authStatus ->
                if (!authStatus.isTimerFinished) return@onEach
                else if (!authStatus.isAuthenticationCompleted) navigateToAuthSelectionEvent.call()
                else if (authStatus.isDataLoaded) navigateToHomeEvent.call()
            }
            .launchIn(viewModelScope)

        startTimer()
        checkIsAuthenticationCompleted()
        loadData()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(1500)
            isTimerFinishedSourceFlow.value = true
        }
    }

    private fun checkIsAuthenticationCompleted() {
        val currentUser = firebaseRepo.getCurrentUser()
        isAuthenticationCompletedSourceFlow.value = currentUser != null
    }

    private fun loadData() {
        firebaseRepo.loadEnglishIrregularVerbs { loadStatus ->
            isEnglishDataLoadedSourceFlow.value =
                loadStatus == LoadStatus.Success || loadStatus == LoadStatus.Empty
        }
        firebaseRepo.loadSpanishVerbs { loadStatus ->
            isSpanishDataLoadedSourceFlow.value =
                loadStatus == LoadStatus.Success || loadStatus == LoadStatus.Empty
        }
    }

    data class AuthStatus(
        val isTimerFinished: Boolean,
        val isAuthenticationCompleted: Boolean,
        val isDataLoaded: Boolean
    )
}