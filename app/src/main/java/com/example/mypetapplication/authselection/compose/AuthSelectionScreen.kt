package com.example.mypetapplication.authselection.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.R
import com.example.mypetapplication.authselection.data.AuthSelectionScreenContent
import com.example.presentationmodule.compose.button.MyPetButton
import com.example.presentationmodule.compose.button.MyPetButtonState

@Composable
fun AuthSelectionScreen(
    contentState: State<AuthSelectionScreenContent?>,
) {
    val content = contentState.value ?: return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        val isSignInState = content.isSignInState.observeAsState(initial = false).value
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isScreenBlocked = content.isScreenBlocked.observeAsState(initial = false).value
            val emailFromContent = content.email.observeAsState(initial = "").value
            EmailCell(
                email = emailFromContent,
                isScreenBlocked = isScreenBlocked,
                callback = { content.onEmailChanged.invoke(it) })
            Spacer(modifier = Modifier.height(16.dp))
            val passwordFromContent = content.password.observeAsState(initial = "").value
            PasswordCell(
                password = passwordFromContent,
                isScreenBlocked = isScreenBlocked,
                callback = { content.onPasswordChanged.invoke(it) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            val authButtonCallback = content.onAuthButtonClicked
            val authButtonState =
                content.buttonState.observeAsState(initial = MyPetButtonState.Disable).value
            val authButtonTextResId = if (isSignInState) R.string.sign_in else R.string.sign_up
            MyPetButton(textResId = authButtonTextResId, state = authButtonState) {
                authButtonCallback.invoke()
            }
            Spacer(modifier = Modifier.height(16.dp))
            val additionalTextResId = content.additionalText.observeAsState().value
            val additionalTextCallback = content.onSwitchAuthTypeClicked
            additionalTextResId?.let { textResId ->
                AdditionalTextCell(
                    textResId
                ) { additionalTextCallback.invoke() }
            }

        }
    }
}

@Composable
private fun EmailCell(email: String, isScreenBlocked: Boolean, callback: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { emailNewValue ->
            callback.invoke(emailNewValue)
        },
        enabled = !isScreenBlocked,
        label = { Text("Email") },
        maxLines = 1
    )
}

@Composable
private fun PasswordCell(password: String, isScreenBlocked: Boolean, callback: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { passwordNewValue ->
            callback.invoke(passwordNewValue)
        },
        enabled = !isScreenBlocked,
        label = { Text("Password") },
        maxLines = 1
    )
}

@Composable
private fun AdditionalTextCell(textResId: Int, callback: () -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { callback.invoke() },
        text = stringResource(id = textResId),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp
    )
}