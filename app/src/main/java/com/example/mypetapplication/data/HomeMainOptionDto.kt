package com.example.mypetapplication.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class HomeMainOptionDto(
    val type: HomeMainOptionType,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int? = null,
    @ColorRes val solidColorResId: Int,
    val onHomeMainOptionItemClicked: ((HomeMainOptionType) -> Unit)? = null
)
