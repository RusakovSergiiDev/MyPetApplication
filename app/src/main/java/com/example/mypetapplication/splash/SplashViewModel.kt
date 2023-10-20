package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val authStatusSourceFlow =
        combine(
            isTimerFinishedSourceFlow,
            isAuthenticationCompletedSourceFlow
        ) { isTimerFinished, isAuthenticationCompleted ->
            AuthStatus(isTimerFinished, isAuthenticationCompleted)
        }

    val navigateToAuthSelectionEvent = SimpleNavigationEvent()
    val navigateToHomeEvent = SimpleNavigationEvent()

    init {
        authStatusSourceFlow
            .onEach { authStatus ->
                if (!authStatus.isTimerFinished) return@onEach
                when (authStatus.isAuthenticationCompleted) {
                    true -> navigateToHomeEvent.call()
                    false -> navigateToAuthSelectionEvent.call()
                }
            }
            .launchIn(viewModelScope)

        startTimer()
        checkIsAuthenticationCompleted()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3000)
            isTimerFinishedSourceFlow.value = true
        }
    }

    private fun checkIsAuthenticationCompleted() {
        isAuthenticationCompletedSourceFlow.value = false
    }

    data class AuthStatus(
        val isTimerFinished: Boolean,
        val isAuthenticationCompleted: Boolean
    )
}