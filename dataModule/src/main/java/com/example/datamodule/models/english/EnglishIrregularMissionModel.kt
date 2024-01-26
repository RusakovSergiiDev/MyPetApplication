package com.example.datamodule.models.english

import com.example.datamodule.models.EnglishIrregularVerbModel

data class EnglishIrregularMissionModel(
    val englishIrregularVerbModel: EnglishIrregularVerbModel
) {

    fun getInfinitive() = englishIrregularVerbModel.infinitive

    fun getPastSimple() = englishIrregularVerbModel.pastSimple

    fun getPastParticiple() = englishIrregularVerbModel.pastParticiple
}
