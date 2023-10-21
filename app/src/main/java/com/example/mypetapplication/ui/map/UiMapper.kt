package com.example.mypetapplication.ui.map

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.mypetapplication.home.data.HomeMainOptionItem
import com.example.mypetapplication.ui.data.HomeMainOptionUIiItem

private fun HomeMainOptionItem.map(context: Context): HomeMainOptionUIiItem =
    HomeMainOptionUIiItem(
        type = this.type,
        title = context.getString(this.titleResId),
        description = this.descriptionResId?.let { context.getString(it) },
        solidColor = Color(ContextCompat.getColor(context, this.solidColorResId)),
        onHomeMainOptionItemClicked = { this.onHomeMainOptionItemClicked?.invoke(this.type) }
    )

fun List<HomeMainOptionItem>.map(context: Context): List<HomeMainOptionUIiItem> =
    this.map { item -> item.map(context) }