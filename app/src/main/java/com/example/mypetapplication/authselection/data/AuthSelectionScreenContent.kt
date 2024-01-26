package com.example.mypetapplication.authselection.data

import androidx.lifecycle.LiveData
import com.example.mypetapplication.base.IScreenContent
import com.example.presentationmodule.compose.button.MyPetButtonState

data class AuthSelectionScreenContent(
    val isSignInState: LiveData<Boolean>,
    val email: LiveData<String>,
    val password: LiveData<String>,
    val isScreenBlocked: LiveData<Boolean>,
    val buttonState: LiveData<MyPetButtonState>,
    val isShowAuthError: LiveData<String?>,
    val additionalText: LiveData<Int>,
    val onEmailChanged: (String) -> Unit,
    val onPasswordChanged: (String) -> Unit,
    val onAuthButtonClicked: () -> Unit,
    val onSwitchAuthTypeClicked: () -> Unit
) : IScreenContent
