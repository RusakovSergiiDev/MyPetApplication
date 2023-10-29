package com.example.mypetapplication.home.map

import android.content.Context
import com.example.datamodule.models.HomeMainOptionModel
import com.example.presentationmodule.data.HomeMainOptionUIiItem

class HomeUiMapper(private val context: Context) {
    private fun HomeMainOptionModel.mapToUiItem(): HomeMainOptionUIiItem =
        HomeMainOptionUIiItem(
            title = context.getString(this.titleResId),
            description = this.descriptionResId?.let { context.getString(it) },
            onHomeMainOptionItemClicked = { this.onHomeMainOptionItemClicked?.invoke(this.type) }
        )

    fun mapToUiItems(items: List<HomeMainOptionModel>): List<HomeMainOptionUIiItem> {
        return items.map { item -> item.mapToUiItem() }
    }
}