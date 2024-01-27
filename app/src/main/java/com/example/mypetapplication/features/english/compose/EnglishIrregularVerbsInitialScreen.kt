package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsState

@Composable
fun EnglishIrregularVerbsInitialScreen(
    content: EnglishIrregularVerbsState.Initial
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = { content.callback.invoke() }
        ) {
            Text(text = "Start")
        }
    }
}