package com.example.mypetapplication.home.map

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.mypetapplication.data.HomeMainOptionDto
import com.example.mypetapplication.ui.data.HomeMainOptionUIiItem

class HomeUiMapper(private val context: Context) {
    private fun HomeMainOptionDto.mapToUiItem(): HomeMainOptionUIiItem =
        HomeMainOptionUIiItem(
            type = this.type,
            title = context.getString(this.titleResId),
            description = this.descriptionResId?.let { context.getString(it) },
            solidColor = Color(ContextCompat.getColor(context, this.solidColorResId)),
            onHomeMainOptionItemClicked = { this.onHomeMainOptionItemClicked?.invoke(this.type) }
        )

    fun mapToUiItems(items: List<HomeMainOptionDto>): List<HomeMainOptionUIiItem> {
        return items.map { item -> item.mapToUiItem() }
    }
}