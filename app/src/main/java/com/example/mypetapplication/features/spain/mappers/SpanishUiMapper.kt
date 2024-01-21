package com.example.mypetapplication.features.spain.mappers

import com.example.datamodule.models.SpanishVerbModel
import com.example.presentationmodule.data.SpanishVerbUiItem
import javax.inject.Inject

class SpanishUiMapper @Inject constructor() {

    fun mapToUiItem(item: SpanishVerbModel): SpanishVerbUiItem =
        SpanishVerbUiItem(
            spanish = item.spanish,
            ukrainian = item.ukrainian
        )

    fun mapToUiItems(items: List<SpanishVerbModel>): List<SpanishVerbUiItem> {
        return items.map { item -> mapToUiItem(item) }
    }
}