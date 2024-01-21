package com.example.mypetapplication.authselection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.types.Task
import com.example.datamodule.types.isLoading
import com.example.datamodule.types.isSuccess
import com.example.logicmodule.usecases.firebase.TryToSignInUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.authselection.data.AuthSelectionScreenContent
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.mypetapplication.utils.undefined
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthSelectionViewModel @Inject constructor(
    private val tryToSignInUseCase: TryToSignInUseCase
) : BaseContentViewModel<AuthSelectionScreenContent>() {

    // Internal param(s)
    private val authProgressTaskFlowSource = MutableStateFlow<Task<Unit>>(Task.Initial)
    private val isAuthLoadingFlowSource = authProgressTaskFlowSource.map { it.isLoading() }
    private val isSignInStateFlowSource = MutableStateFlow(true)
    private val emailFlowSource = MutableStateFlow("rusakov.sergii.dev@gmail.com")
    private val passwordFlowSource = MutableStateFlow("Polinom314")
    private val isInputEnableFlowSource = authProgressTaskFlowSource.map { !it.isLoading() }
    private val isButtonEnableFlowSource =
        combine(
            emailFlowSource,
            passwordFlowSource,
            isAuthLoadingFlowSource
        ) { email, password, isLoading ->
            email.isNotEmpty() && password.isNotEmpty() && !isLoading
        }
    private val additionalTextResIdFlowSource = isSignInStateFlowSource.map {
        if (it) R.string.label_trySignUp else R.string.label_trySignIn
    }
    private val isShowAuthErrorFlowSource = authProgressTaskFlowSource.map {
        if (it is Task.Error) {
            it.errorMessage
        } else {
            null
        }
    }
    private val contentLiveDataSource = MutableLiveData(
        AuthSelectionScreenContent(
            isSignInStateFlowSource.asLiveData(),
            emailFlowSource.asLiveData(),
            passwordFlowSource.asLiveData(),
            isInputEnableFlowSource.asLiveData(),
            isButtonEnableFlowSource.asLiveData(),
            isLoading = isAuthLoadingFlowSource.asLiveData(),
            isShowAuthError = isShowAuthErrorFlowSource.asLiveData(),
            additionalText = additionalTextResIdFlowSource.asLiveData(),
            onEmailChanged = { onEmailChanged(it) },
            onPasswordChanged = { onPasswordChanged(it) },
            onSwitchAuthTypeClicked = { onSwitchAuthTypeClicked() },
            onAuthButtonClicked = { onAuthButtonClicked() },
        )
    )

    // Base fun(s)
    override fun getTopAppBarTitleResId() = undefined

    // Event(s)
    val navigateToHomeEvent = SimpleNavigationEvent()

    init {
        authProgressTaskFlowSource.onEach {
            if (it.isSuccess()) navigateToHomeEvent.call()
        }.launchIn(viewModelScope)
        registerContentSource(contentLiveDataSource)
    }

    private fun onEmailChanged(email: String) {
        emailFlowSource.value = email
    }

    private fun onPasswordChanged(password: String) {
        passwordFlowSource.value = password
    }

    private fun onSwitchAuthTypeClicked() {
        val isSignInState = isSignInStateFlowSource.value
        isSignInStateFlowSource.value = !isSignInState
    }

    private fun onAuthButtonClicked() {
        val isSignInState = isSignInStateFlowSource.value
        if (isSignInState) {
            tryToSignIn()
        } else {
            tryToSignIn()
        }
    }

    private fun tryToSignIn() {
        val email = emailFlowSource.value
        val password = passwordFlowSource.value
        viewModelScope.launch {
            tryToSignInUseCase.execute(email, password) { task ->
                authProgressTaskFlowSource.value = task
                if (task is Task.Error) {
                    snackbarErrorEvent.value = task.errorMessage
                } else {
                    snackbarErrorEvent.value = null
                }
            }
        }
    }
}