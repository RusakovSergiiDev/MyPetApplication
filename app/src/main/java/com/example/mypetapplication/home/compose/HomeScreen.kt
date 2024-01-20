package com.example.mypetapplication.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        if (items.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items.forEach { item ->
            HomeMainOptionItemCell(item)
        }
        if (items.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}