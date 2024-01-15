package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.logicmodule.usecases.GetFirebaseCurrentUserUseCase
import com.example.logicmodule.usecases.GetInitialDataUseCase
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
    private val getFirebaseCurrentUserUseCase: GetFirebaseCurrentUserUseCase,
    private val getInitialDataUseCase: GetInitialDataUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val isAllNecessaryDataLoadedFlow = MutableStateFlow(false)
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
        loadInitialData()
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

    private fun loadInitialData() {
        viewModelScope.launch {
            getInitialDataUseCase.execute().collect {
                isAllNecessaryDataLoadedFlow.value = it
            }
        }
    }

    data class AuthStatus(
        val isTimerFinished: Boolean,
        val isAuthenticationCompleted: Boolean,
        val isDataLoaded: Boolean
    )
}