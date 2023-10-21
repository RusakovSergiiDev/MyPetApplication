package com.example.mypetapplication.home.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.mypetapplication.ui.HomeMainOptionItemCell
import com.example.mypetapplication.ui.data.HomeMainOptionUIiItem

@Composable
fun HomeScreen(
    homeMainOptionUiItemsState: State<List<HomeMainOptionUIiItem>>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            homeMainOptionUiItemsState.value.forEach {
                HomeMainOptionItemCell(it)
            }
        }
    }
}