package com.example.mypetapplication.features.english.map

import android.content.Context
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.mypetapplication.ui.data.EnglishIrregularVerbUiItem

class EnglishUiMapper(private val context: Context) {

    private fun EnglishIrregularVerbModel.mapToUiItem(): EnglishIrregularVerbUiItem? {
        return EnglishIrregularVerbUiItem(
            infinitive = this.infinitive,
            pastSimple = this.pastSimple,
            pastParticiple = this.pastParticiple,
            translateInUkrainian = this.translateInUkrainian
        )
    }

    fun mapToUiItems(items: List<EnglishIrregularVerbModel>): List<EnglishIrregularVerbUiItem> {
        val result = items.mapNotNull { item -> item.mapToUiItem() }
        return result
    }
}