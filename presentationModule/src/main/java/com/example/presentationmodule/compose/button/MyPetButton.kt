package com.example.presentationmodule.compose.button

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MyPetButton(
    @StringRes textResId: Int,
    state: MyPetButtonState,
    callback: () -> Unit
) {
    Button(
        onClick = { callback.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = if (state == MyPetButtonState.Error) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
                shape = RoundedCornerShape(8.dp)
            ),
        enabled = state == MyPetButtonState.Enable
    ) {
        if (state == MyPetButtonState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = stringResource(id = textResId).uppercase(),
                color = if (state == MyPetButtonState.Error) {
                    MaterialTheme.colorScheme.onError
                } else {
                    MaterialTheme.colorScheme.onPrimary
                }
            )
        }
    }
}