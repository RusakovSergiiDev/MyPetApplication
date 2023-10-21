package com.example.mypetapplication.ui.data

import androidx.compose.ui.graphics.Color
import com.example.mypetapplication.home.data.HomeMainOptionType

data class HomeMainOptionUIiItem(
    val type: HomeMainOptionType,
    val title: String,
    val description: String? = null,
    val solidColor: Color,
    val onHomeMainOptionItemClicked: ((HomeMainOptionType) -> Unit)? = null
)
