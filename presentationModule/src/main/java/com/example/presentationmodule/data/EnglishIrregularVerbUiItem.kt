package com.example.presentationmodule.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

data class EnglishIrregularVerbUiItem(
    val index: Int,
    val infinitive: String,
    val pastSimple: String,
    val pastParticiple: String,
    val translateInUkrainian: String,
) {

    private val isShowTranslateInUkrainianStateSource = mutableStateOf(false)

    val isShowTranslateInUkrainianState: State<Boolean> = isShowTranslateInUkrainianStateSource

    fun setIsShowTranslateInUkrainian(isShow: Boolean) {
        isShowTranslateInUkrainianStateSource.value = isShow
    }
}