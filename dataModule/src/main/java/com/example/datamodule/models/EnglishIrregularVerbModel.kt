package com.example.datamodule.models

data class EnglishIrregularVerbModel(
    val infinitive: String,
    val pastSimple: String,
    val pastParticiple: String,
    val translateInUkrainian: String,
    val isPopular: Boolean,
    var index: Int = 0,
)