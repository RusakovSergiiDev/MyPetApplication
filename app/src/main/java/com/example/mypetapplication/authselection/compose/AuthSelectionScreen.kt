package com.example.mypetapplication.authselection.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthSelectionScreen(
    isSignInState: State<Boolean>,
    emailState: State<String>,
    passwordState: State<String>,
    isSignInEnableState: State<Boolean>,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSwitchToSignIn: () -> Unit,
    onSwitchToSignUp: () -> Unit,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        val isSignIn = isSignInState.value
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailState.value,
                onValueChange = { email ->
                    onEmailChanged.invoke(email)
                },
                label = { Text("Email") },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordState.value,
                onValueChange = { password ->
                    onPasswordChanged.invoke(password)
                },
                label = { Text("Password") },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(24.dp))
            val signButtonText = if (isSignIn) "SIGN IN" else "SIGN UP"
            val signButtonCallback = if (isSignIn) onSignInClicked else onSignUpClicked
            Button(
                onClick = { signButtonCallback.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isSignInEnableState.value
            ) {
                Text(signButtonText)
            }
            Spacer(modifier = Modifier.height(16.dp))
            val additionalText =
                if (isSignIn) "Don't have an account? Sign Up" else "Do you already have an account? Sign In"
            val additionalTextCallback = if (isSignIn) onSwitchToSignUp else onSwitchToSignIn
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        additionalTextCallback.invoke()
                    },
                text = additionalText,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp
            )
        }
    }
}