package com.example.mypetapplication.features.spain.map

import com.example.datamodule.models.SpanishVerbModel
import com.example.presentationmodule.data.SpanishVerbUiItem

class SpanishUiMapper {

    private fun SpanishVerbModel.mapToUiItem(): SpanishVerbUiItem =
        SpanishVerbUiItem(
            index = index,
            spanish = this.spanish,
            ukrainian = this.ukrainian
        )

    fun mapToUiItems(items: List<SpanishVerbModel>): List<SpanishVerbUiItem> {
        return items.map { item -> item.mapToUiItem() }
    }
}