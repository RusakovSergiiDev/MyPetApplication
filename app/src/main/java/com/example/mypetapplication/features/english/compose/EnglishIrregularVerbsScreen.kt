package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EnglishIrregularVerbsScreen(
    onSeeAllClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            Text(
                modifier = Modifier.clickable { onSeeAllClicked.invoke() },
                text = "See All"
            )
        }
    }
}