package com.example.mypetapplication.authselection.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailFromContent,
                onValueChange = { email ->
                    content.onEmailChanged.invoke(email)
                },
                enabled = !isScreenBlocked,
                label = { Text("Email") },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            val passwordFromContent = content.password.observeAsState(initial = "").value
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordFromContent,
                onValueChange = { email ->
                    content.onPasswordChanged.invoke(email)
                },
                enabled = !isScreenBlocked,
                label = { Text("Password") },
                maxLines = 1
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { additionalTextCallback.invoke() },
                    text = stringResource(id = textResId),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )
            }

        }
    }
}