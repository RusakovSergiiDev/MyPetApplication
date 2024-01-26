package com.example.mypetapplication.features.english.data

import androidx.compose.runtime.State
import com.example.datamodule.models.english.EnglishIrregularCampaignModel
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.mypetapplication.base.IScreenContent

class EnglishIrregularVerbsScreenContent(
    val campaign: EnglishIrregularCampaignModel,
    val currentMissionIndex: State<Int>,
    val onMissionCheckCallback: (EnglishIrregularMissionModel) -> Unit
) : IScreenContent