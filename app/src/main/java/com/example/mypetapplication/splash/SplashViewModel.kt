package com.example.mypetapplication.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.types.Task
import com.example.datamodule.types.isSuccessOrEmpty
import com.example.logicmodule.usecases.GetFeatureListFlowTaskUseCase
import com.example.logicmodule.usecases.firebase.GetFirebaseCurrentUserUseCase
import com.example.logicmodule.usecases.firebase.GetFirebaseInitialDataUseCase
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.splash.data.SplashScreenContent
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.mypetapplication.utils.undefined
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
) : BaseContentViewModel<SplashScreenContent>() {

    // Internal param(s)
    private val isTimerFinishedFlowSource = MutableStateFlow(false)
    private val isAuthenticationCompletedFlowSource = MutableStateFlow(false)
    private val isAllNecessaryFirebaseDataLoadedFlowSource = MutableStateFlow(false)
    private val isAllNecessaryServerDataLoadedFlowSource = MutableStateFlow(false)
    private val authStatusFlowSource =
        combine(
            isTimerFinishedFlowSource,
            isAuthenticationCompletedFlowSource,
            isAllNecessaryFirebaseDataLoadedFlowSource,
            isAllNecessaryServerDataLoadedFlowSource,
        ) { isTimerFinished, isAuthenticationCompleted, isAllNecessaryDataLoaded, isAllNecessaryServerDataLoaded ->
            AuthStatus(
                isTimerFinished,
                isAuthenticationCompleted,
                isAllNecessaryDataLoaded,
                isAllNecessaryServerDataLoaded
            )
        }
    private val contentLiveDataSource = MutableLiveData(SplashScreenContent(true))

    // Base fun(s)
    override fun getTopAppBarTitleResId() = undefined

    // Event(s)
    val navigateToAuthSelectionEvent = SimpleNavigationEvent()
    val navigateToHomeEvent = SimpleNavigationEvent()

    init {
        authStatusFlowSource
            .onEach { authStatus ->
                if (!authStatus.isTimerFinished) return@onEach
                else if (!authStatus.isAuthenticationCompleted) navigateToAuthSelectionEvent.call()
                else if (authStatus.isFirebaseDataLoaded && !authStatus.isServerDataLoaded) navigateToHomeEvent.call()
            }
            .launchIn(viewModelScope)

        startTimer()
        checkIsAuthenticationCompleted()
        registerContentSource(contentLiveDataSource)
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3000)
            isTimerFinishedFlowSource.value = true
        }
    }

    private fun checkIsAuthenticationCompleted() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = getFirebaseCurrentUserUseCase.execute()
            val isSignedUp = currentUser != null
            isAuthenticationCompletedFlowSource.value = isSignedUp
            if (isSignedUp) {
                loadInitialData()
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            getFirebaseInitialDataUseCase.execute().collect {
                isAllNecessaryFirebaseDataLoadedFlowSource.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            executeAndWrapUseCase(getFeatureListUseCase).collect { task ->
                isAllNecessaryServerDataLoadedFlowSource.value = task.isSuccessOrEmpty()
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