package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsState
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsScreenContent

@Composable
fun EnglishIrregularVerbsScreen(
    contentState: State<EnglishIrregularVerbsScreenContent?>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val content = contentState.value ?: return@Box
        val screenTypeState = content.state.value
        when (screenTypeState) {
            is EnglishIrregularVerbsState.Initial -> {
                EnglishIrregularVerbsInitialScreen(
                    screenTypeState
                )
            }

            is EnglishIrregularVerbsState.Campaign ->
                EnglishIrregularVerbsCampaignScreen(
                    screenTypeState
                )

            is EnglishIrregularVerbsState.Finish -> {
                Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}