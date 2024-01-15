package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.datamodule.types.LoadStatus
import com.example.logicmodule.ContentRepository
import com.example.logicmodule.FirebaseRepository
import com.example.logicmodule.usecases.GetFirebaseCurrentUserUseCase
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val getFirebaseCurrentUserUseCase: GetFirebaseCurrentUserUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val isEnglishDataLoadedSourceFlow = MutableStateFlow(false)
    private val isSpanishDataLoadedSourceFlow = MutableStateFlow(false)
    private val isAllNecessaryDataLoadedFlow =
        combine(
            isEnglishDataLoadedSourceFlow,
            isSpanishDataLoadedSourceFlow
        ) { isEnglishDataLoaded, isSpanishDataLoaded ->
            isEnglishDataLoaded && isSpanishDataLoaded
        }
    private val authStatusSourceFlow =
        combine(
            isTimerFinishedSourceFlow,
            isAuthenticationCompletedSourceFlow,
            isAllNecessaryDataLoadedFlow,
        ) { isTimerFinished, isAuthenticationCompleted, isAllNecessaryDataLoaded ->
            AuthStatus(
                isTimerFinished,
                isAuthenticationCompleted,
                isAllNecessaryDataLoaded,
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
        val currentUser = getFirebaseCurrentUserUseCase.execute()
        isAuthenticationCompletedSourceFlow.value = currentUser != null
    }

    private fun loadData() {
        firebaseRepository.loadEnglishIrregularVerbs { loadStatus ->
            isEnglishDataLoadedSourceFlow.value =
                loadStatus == LoadStatus.Success || loadStatus == LoadStatus.Empty
        }
        firebaseRepository.loadSpanishVerbs { loadStatus ->
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