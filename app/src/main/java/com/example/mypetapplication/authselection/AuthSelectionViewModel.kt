package com.example.mypetapplication.authselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class AuthSelectionViewModel : BaseViewModel() {

    // Internal param(s)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val isSignInSourceFlow = MutableStateFlow(true)
    private val emailSourceFlow = MutableStateFlow("")
    private val passwordSourceFlow = MutableStateFlow("")
    private val isSignInEnableSourceFlow =
        combine(emailSourceFlow, passwordSourceFlow) { email, password ->
            email.isNotEmpty() && password.isNotEmpty()
        }

    // External param(s)
    val isSignInLiveData = isSignInSourceFlow.asLiveData()
    val emailLiveData: LiveData<String> = emailSourceFlow.asLiveData()
    val passwordLiveData: LiveData<String> = passwordSourceFlow.asLiveData()
    val isSignInEnableLiveData: LiveData<Boolean> = isSignInEnableSourceFlow.asLiveData()

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
        isSignInSourceFlow.value = true
    }

    fun switchToSignUp() {
        isSignInSourceFlow.value = false
    }

    private fun tryToSignIn() {
        val email = emailSourceFlow.value
        val password = passwordSourceFlow.value
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHomeEvent.call()
                } else {

                }
            }
    }
}