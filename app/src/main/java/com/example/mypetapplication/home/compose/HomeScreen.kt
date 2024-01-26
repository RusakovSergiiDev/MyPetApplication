package com.example.mypetapplication.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.mypetapplication.home.data.HomeScreenContent
import com.example.presentationmodule.compose.HomeMainOptionItemCell

@Composable
fun HomeScreen(
    contentState: State<HomeScreenContent?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        val items = contentState.value?.homeOptions ?: emptyList()
        items.forEachIndexed { index, homeMainOptionUiItem ->
            HomeMainOptionItemCell(homeMainOptionUiItem)
            if (index != items.lastIndex) {
                Divider()
            }
        }
    }
}