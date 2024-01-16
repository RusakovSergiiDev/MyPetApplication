package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentationmodule.AppTheme
import com.example.presentationmodule.R
import com.example.presentationmodule.compose.topappbar.BackTopAppBarWithAction

@Composable
fun EnglishRulesScreen(
    onBackClicked: () -> Unit,
    isLoadingState: State<Boolean>,
    onRetryClicked: () -> Unit,
) {
    AppTheme {
        BackTopAppBarWithAction(
            titleResId = R.string.label_englishRules,
            onBackClicked = { onBackClicked.invoke() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoadingState.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                } else {
                    Button(
                        onClick = { onRetryClicked.invoke() },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}