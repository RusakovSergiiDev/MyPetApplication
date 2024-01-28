package com.example.datamodule.models.english

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

data class EnglishIrregularCampaignModel(
    val missions: List<EnglishIrregularMissionModel>,
    private val currentMissionIndex: MutableState<Int>,
    val onMissionCheckCallback: ((EnglishIrregularMissionModel) -> Unit)? = null
) {

    fun setCurrentMissionIndex(index: Int) {
        currentMissionIndex.value = index
    }

    fun getCurrentMissionIndexState(): State<Int> = currentMissionIndex
}
