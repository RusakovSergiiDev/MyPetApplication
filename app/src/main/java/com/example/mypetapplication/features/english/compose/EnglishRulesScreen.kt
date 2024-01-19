package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import com.example.mypetapplication.base.BaseFullComposeScreenContent
import com.example.mypetapplication.features.english.data.EnglishRulesScreenContent

@Composable
fun EnglishRulesScreen(
    content: LiveData<BaseFullComposeScreenContent<EnglishRulesScreenContent>>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
//        if (isLoadingState.value) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .size(50.dp)
//                    .align(Alignment.Center)
//            )
//        } else {
//            Button(
//                onClick = { onRetryClicked.invoke() },
//                modifier = Modifier.align(Alignment.Center)
//            ) {
//                Text("Retry")
//            }
//        }
    }
}