package com.example.datamodule.models.english

import androidx.compose.runtime.State

data class EnglishIrregularCampaignModel(
    val missions: List<EnglishIrregularMissionModel>,
    val currentMissionIndex: State<Int>,
    val onMissionCheckCallback: (EnglishIrregularMissionModel) -> Unit
)
