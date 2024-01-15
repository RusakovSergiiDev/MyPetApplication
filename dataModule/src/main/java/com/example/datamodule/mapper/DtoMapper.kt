package com.example.datamodule.mapper

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel

fun List<EnglishIrregularVerbDto>.mapToEnglishIrregularVerbModel(): List<EnglishIrregularVerbModel> {
    return this.mapNotNull { item ->
        val infinitive = item.infinitive ?: return@mapNotNull null
        val simplePast = item.pastSimple ?: return@mapNotNull null
        val pastParticiple = item.pastParticiple ?: return@mapNotNull null
        val translateInUkrainian = item.translateInUkrainian ?: return@mapNotNull null
        val isPopular = item.isPopular ?: false
        EnglishIrregularVerbModel(
            infinitive = infinitive,
            pastSimple = simplePast,
            pastParticiple = pastParticiple,
            translateInUkrainian = translateInUkrainian,
            isPopular = isPopular
        )
    }
}

fun List<SpanishVerbDto>.mapSpanishVerbModel(): List<SpanishVerbModel> {
    return this.mapNotNull { item ->
        val english = item.en ?: return@mapNotNull null
        val spanish = item.es ?: return@mapNotNull null
        val ukrainian = item.ua ?: return@mapNotNull null
        SpanishVerbModel(
            english = english,
            spanish = spanish,
            ukrainian = ukrainian
        )
    }
}