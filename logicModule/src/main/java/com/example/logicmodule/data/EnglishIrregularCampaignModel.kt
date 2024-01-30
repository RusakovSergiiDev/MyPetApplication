package com.example.logicmodule.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.example.datamodule.models.english.EnglishIrregularMissionModel

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
