package com.example.datamodule.models

import androidx.annotation.StringRes
import com.example.datamodule.types.HomeMainOptionType

data class HomeMainOptionModel(
    val type: HomeMainOptionType,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int? = null,
    val onHomeMainOptionItemClicked: ((HomeMainOptionType) -> Unit)? = null
)
