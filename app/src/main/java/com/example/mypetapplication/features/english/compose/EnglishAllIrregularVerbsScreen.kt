package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.mypetapplication.ui.data.EnglishIrregularVerbUiItem
import com.example.mypetapplication.ui.EnglishIrregularVerbRowCell

@Composable
fun EnglishAllIrregularVerbsScreen(englishIrregularVerbUiItemsState: State<List<EnglishIrregularVerbUiItem>>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            englishIrregularVerbUiItemsState.value.forEach { item ->
                EnglishIrregularVerbRowCell(item)
            }
        }
    }
}