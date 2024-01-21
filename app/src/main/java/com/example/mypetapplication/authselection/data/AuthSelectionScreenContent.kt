package com.example.mypetapplication.authselection.data

import androidx.lifecycle.LiveData
import com.example.mypetapplication.base.IBaseScreenContent

data class AuthSelectionScreenContent(
    val isSignInState: LiveData<Boolean>,
    val email: LiveData<String>,
    val password: LiveData<String>,
    val isInputEnable: LiveData<Boolean>,
    val isButtonEnable: LiveData<Boolean>,
    val isLoading: LiveData<Boolean>,
    val isShowAuthError: LiveData<String?>,
    val additionalText: LiveData<Int>,
    val onEmailChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onAuthButtonClicked: () -> Unit,
    val onSwitchAuthTypeClicked: () -> Unit
) : IBaseScreenContent
