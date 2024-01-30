package com.example.mypetapplication.authselection

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.types.Task
import com.example.datamodule.types.isError
import com.example.datamodule.types.isLoading
import com.example.datamodule.types.isSuccess
import com.example.logicmodule.usecases.firebase.TryToSignInUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.authselection.data.AuthSelectionScreenContent
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.compose.button.MyPetButtonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSelectionViewModel @Inject constructor(
    private val tryToSignInUseCase: TryToSignInUseCase
) : BaseContentViewModel<AuthSelectionScreenContent>() {

    // Internal param(s)
    private val authProgressTaskFlowSource = MutableStateFlow<Task<Unit>>(Task.Initial)
    private val isSignInStateFlowSource = MutableStateFlow(true)
    private val emailFlowSource = MutableStateFlow("rusakov.sergii.dev@gmail.com")
    private val passwordFlowSource = MutableStateFlow("Polinom314")
    private val isScreenBlockedFlowSource = authProgressTaskFlowSource.map { it.isLoading() }
    private val isInputValidFlowSource =
        combine(
            emailFlowSource,
            passwordFlowSource
        ) { email, password ->
            email.isNotEmpty() && password.isNotEmpty()
        }
    private val authButtonStateFlowSource =
        combine(
            isInputValidFlowSource,
            authProgressTaskFlowSource
        ) { isInputValid, authProgress ->
            if (authProgress.isLoading()) {
                MyPetButtonState.Loading
            } else if (authProgress.isError()) {
                MyPetButtonState.Error
            } else {
                if (isInputValid) MyPetButtonState.Enable else MyPetButtonState.Disable
            }
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
    private var showAndHideErrorSnackbarJob: Job? = null

    // Event(s)
    val navigateToHomeEvent = SimpleNavigationEvent()

    override val screenContentFlow: Flow<AuthSelectionScreenContent> =
        MutableStateFlow(
            AuthSelectionScreenContent(
                isSignInStateFlowSource.asLiveData(),
                emailFlowSource.asLiveData(),
                passwordFlowSource.asLiveData(),
                isScreenBlockedFlowSource.asLiveData(),
                authButtonStateFlowSource.asLiveData(),
                isShowAuthError = isShowAuthErrorFlowSource.asLiveData(),
                additionalText = additionalTextResIdFlowSource.asLiveData(),
                onEmailChanged = { onEmailChanged(it) },
                onPasswordChanged = { onPasswordChanged(it) },
                onSwitchAuthTypeClicked = { onSwitchAuthTypeClicked() },
                onAuthButtonClicked = { onAuthButtonClicked() },
            )
        )

    init {
        setIsShowTopAppBar(false)

        registerScreenContentSource(screenContentFlow)

        authProgressTaskFlowSource.onEach {
            if (it.isSuccess()) {
                navigateToHomeEvent.call()
            } else if (it is Task.Error) {
                showErrorSnackbar(it.errorMessage)
            } else if (it.isLoading()) {
                showAndHideErrorSnackbarJob?.cancel()
                hideErrorSnackbar()
            }
        }.launchIn(viewModelScope)
    }

    private fun onEmailChanged(email: String) {
        emailFlowSource.value = email
        authProgressTaskFlowSource.value = Task.Initial
    }

    private fun onPasswordChanged(password: String) {
        passwordFlowSource.value = password
        authProgressTaskFlowSource.value = Task.Initial
    }

    private fun onSwitchAuthTypeClicked() {
        val isSignInState = isSignInStateFlowSource.value
        isSignInStateFlowSource.value = !isSignInState
        authProgressTaskFlowSource.value = Task.Initial
    }

    private fun onAuthButtonClicked() {
        val isSignInState = isSignInStateFlowSource.value
        if (isSignInState) {
            tryToSignIn()
        } else {
            tryToSignUp()
        }
    }

    private fun tryToSignIn() {
        hideErrorSnackbar()
        val email = emailFlowSource.value
        val password = passwordFlowSource.value
        viewModelScope.launch {
            tryToSignInUseCase.execute(email, password) { task ->
                authProgressTaskFlowSource.value = task
            }
        }
    }

    private fun tryToSignUp() {
//        val email = emailFlowSource.value
//        val password = passwordFlowSource.value
//        viewModelScope.launch {
//            tryToSignInUseCase.execute(email, password) { task ->
//                authProgressTaskFlowSource.value = task
//                if (task is Task.Error) {
//                    showErrorSnackbar(task.errorMessage)
//                }
//            }
//        }
    }

    @Suppress("MagicNumber")
    private fun showErrorSnackbar(error: String) {
        showAndHideErrorSnackbarJob = viewModelScope.launch {
            snackbarErrorEvent.value = error
            delay(5000)
            hideErrorSnackbar()
        }
    }

    private fun hideErrorSnackbar() {
        snackbarErrorEvent.value = null
    }
}