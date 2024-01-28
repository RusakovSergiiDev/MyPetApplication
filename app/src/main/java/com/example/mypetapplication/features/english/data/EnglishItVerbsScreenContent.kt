package com.example.mypetapplication.features.english.data

import com.example.datamodule.models.english.EnglishItVerbModel
import com.example.mypetapplication.base.IScreenContent

data class EnglishItVerbsScreenContent(
    val items: List<EnglishItVerbModel>
): IScreenContent