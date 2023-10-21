package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    // Internal param(s)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val authStatusSourceFlow =
        combine(
            isTimerFinishedSourceFlow,
            isAuthenticationCompletedSourceFlow
        ) { isTimerFinished, isAuthenticationCompleted ->
            AuthStatus(isTimerFinished, isAuthenticationCompleted)
        }

    // Event(s)
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
            delay(1500)
            isTimerFinishedSourceFlow.value = true
        }
    }

    private fun checkIsAuthenticationCompleted() {
        val currentUser = firebaseAuth.currentUser
        isAuthenticationCompletedSourceFlow.value = currentUser != null
    }

    data class AuthStatus(
        val isTimerFinished: Boolean,
        val isAuthenticationCompleted: Boolean
    )
}