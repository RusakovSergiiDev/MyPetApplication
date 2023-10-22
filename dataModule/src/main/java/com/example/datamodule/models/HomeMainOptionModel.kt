package com.example.datamodule.models

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.datamodule.types.HomeMainOptionType

data class HomeMainOptionModel(
    val type: HomeMainOptionType,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int? = null,
    @ColorRes val solidColorResId: Int,
    val onHomeMainOptionItemClicked: ((HomeMainOptionType) -> Unit)? = null
)
