package com.example.mypetapplication.features.english.map

import android.content.Context
import com.example.mypetapplication.data.EnglishIrregularVerbDto
import com.example.mypetapplication.ui.data.EnglishIrregularVerbUiItem

class EnglishUiMapper(private val context: Context) {

    private fun EnglishIrregularVerbDto.mapToUiItem(): EnglishIrregularVerbUiItem? {
        val infinitive = this.infinitive ?: return null
        val simplePast = this.pastSimple ?: return null
        val pastParticiple = this.pastParticiple ?: return null
        val translateInUkrainian = this.translateInUkrainian ?: return null
        return EnglishIrregularVerbUiItem(
            infinitive = infinitive,
            pastSimple = simplePast,
            pastParticiple = pastParticiple,
            translateInUkrainian = translateInUkrainian
        )
    }

    fun mapToUiItems(items: List<EnglishIrregularVerbDto>): List<EnglishIrregularVerbUiItem> {
        val result = items.mapNotNull { item -> item.mapToUiItem() }
        return result
    }
}