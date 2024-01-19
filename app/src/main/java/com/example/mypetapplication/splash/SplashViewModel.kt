package com.example.mypetapplication.splash

import androidx.lifecycle.viewModelScope
import com.example.datamodule.types.isSuccessOrEmpty
import com.example.logicmodule.usecases.GetFeatureListFlowTaskUseCase
import com.example.logicmodule.usecases.firebase.GetFirebaseCurrentUserUseCase
import com.example.logicmodule.usecases.firebase.GetFirebaseInitialDataUseCase
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
    private val getFirebaseInitialDataUseCase: GetFirebaseInitialDataUseCase,
    private val getFeatureListUseCase: GetFeatureListFlowTaskUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val isTimerFinishedSourceFlow = MutableStateFlow(false)
    private val isAuthenticationCompletedSourceFlow = MutableStateFlow(false)
    private val isAllNecessaryFirebaseDataLoadedFlow = MutableStateFlow(false)
    private val isAllNecessaryServerDataLoadedFlow = MutableStateFlow(false)
    private val authStatusSourceFlow =
        combine(
            isTimerFinishedSourceFlow,
            isAuthenticationCompletedSourceFlow,
            isAllNecessaryFirebaseDataLoadedFlow,
            isAllNecessaryServerDataLoadedFlow,
        ) { isTimerFinished, isAuthenticationCompleted, isAllNecessaryDataLoaded, isAllNecessaryServerDataLoaded ->
            AuthStatus(
                isTimerFinished,
                isAuthenticationCompleted,
                isAllNecessaryDataLoaded,
                isAllNecessaryServerDataLoaded
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
                else if (authStatus.isFirebaseDataLoaded && !authStatus.isServerDataLoaded) navigateToHomeEvent.call()
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
        val currentUser = getFirebaseCurrentUserUseCase.execute()
        val isSignedUp = currentUser != null
        isAuthenticationCompletedSourceFlow.value = isSignedUp
        if (isSignedUp) {
            loadInitialData()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            getFirebaseInitialDataUseCase.execute().collect {
                isAllNecessaryFirebaseDataLoadedFlow.value = it
            }
        }
        viewModelScope.launch {
            executeAndWrapUseCase(getFeatureListUseCase).collect { task ->
                isAllNecessaryServerDataLoadedFlow.value = task.isSuccessOrEmpty()
            }
        }
    }

    data class AuthStatus(
        val isTimerFinished: Boolean,
        val isAuthenticationCompleted: Boolean,
        val isFirebaseDataLoaded: Boolean,
        val isServerDataLoaded: Boolean
    )
}