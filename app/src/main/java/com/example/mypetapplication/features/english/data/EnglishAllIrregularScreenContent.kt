package com.example.mypetapplication.features.english.data

import com.example.mypetapplication.base.IBaseScreenContent
import com.example.presentationmodule.data.EnglishIrregularVerbUiItem

data class EnglishAllIrregularScreenContent(
    val isShowTranslate: Boolean,
    val items: List<EnglishIrregularVerbUiItem>
) : IBaseScreenContent