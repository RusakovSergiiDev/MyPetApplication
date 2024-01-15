package com.example.mypetapplication.authselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.logicmodule.usecases.TryToSignInUseCase
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AuthSelectionViewModel @Inject constructor(
    private val tryToSignInUseCase: TryToSignInUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val isSignInStateSourceFlow = MutableStateFlow(true)
    private val emailSourceFlow = MutableStateFlow("")
    private val passwordSourceFlow = MutableStateFlow("")
    private val isButtonEnableSourceFlow =
        combine(emailSourceFlow, passwordSourceFlow) { email, password ->
            email.isNotEmpty() && password.isNotEmpty()
        }

    // External param(s)
    val isSignInStateLiveData = isSignInStateSourceFlow.asLiveData()
    val emailLiveData: LiveData<String> = emailSourceFlow.asLiveData()
    val passwordLiveData: LiveData<String> = passwordSourceFlow.asLiveData()
    val isButtonEnableLiveData: LiveData<Boolean> = isButtonEnableSourceFlow.asLiveData()

    // Event(s)
    val navigateToHomeEvent = SimpleNavigationEvent()

    fun onEmailChanged(email: String) {
        emailSourceFlow.value = email
    }

    fun onPasswordChanged(password: String) {
        passwordSourceFlow.value = password
    }

    fun signIn() {
        tryToSignIn()
    }

    fun signUp() {

    }

    fun switchToSignIn() {
        isSignInStateSourceFlow.value = true
    }

    fun switchToSignUp() {
        isSignInStateSourceFlow.value = false
    }

    private fun tryToSignIn() {
        val email = emailSourceFlow.value
        val password = passwordSourceFlow.value
        tryToSignInUseCase.execute(email, password) {
            if (it) navigateToHomeEvent.call()
        }
    }
}