package com.example.mypetapplication.home.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class HomeMainOptionItem(
    val type: HomeMainOptionType,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int? = null,
    @ColorRes val solidColorResId: Int,
    val onHomeMainOptionItemClicked: ((HomeMainOptionType) -> Unit)? = null
)
