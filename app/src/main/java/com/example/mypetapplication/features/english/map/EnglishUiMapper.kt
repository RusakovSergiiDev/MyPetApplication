package com.example.mypetapplication.features.english.map

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.presentationmodule.data.EnglishIrregularVerbUiItem

class EnglishUiMapper {

    private fun EnglishIrregularVerbModel.mapToUiItem(): EnglishIrregularVerbUiItem =
        EnglishIrregularVerbUiItem(
            index = index,
            infinitive = this.infinitive,
            pastSimple = this.pastSimple,
            pastParticiple = this.pastParticiple,
            translateInUkrainian = this.translateInUkrainian
        )

    fun mapToUiItems(items: List<EnglishIrregularVerbModel>): List<EnglishIrregularVerbUiItem> {
        return items.map { item -> item.mapToUiItem() }
    }
}