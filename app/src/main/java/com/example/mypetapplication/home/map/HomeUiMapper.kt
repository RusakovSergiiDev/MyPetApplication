package com.example.mypetapplication.home.map

import android.content.Context
import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.presentationmodule.data.HomeMainOptionUiItem
import javax.inject.Inject

class HomeUiMapper @Inject constructor(private val context: Context) {

    private fun HomeMainOptionModel.mapToUiItem(callback: (HomeMainOptionType) -> Unit): HomeMainOptionUiItem =
        HomeMainOptionUiItem(
            title = context.getString(this.titleResId),
            description = this.descriptionResId?.let { context.getString(it) },
            callback = { callback.invoke(this.type) }
        )

    fun mapToUiItems(
        models: List<HomeMainOptionModel>,
        callback: (HomeMainOptionType) -> Unit
    ): List<HomeMainOptionUiItem> {
        return models.map { model -> model.mapToUiItem(callback) }
    }
}