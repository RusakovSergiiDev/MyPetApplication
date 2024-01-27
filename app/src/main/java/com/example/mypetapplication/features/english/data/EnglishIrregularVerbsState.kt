package com.example.mypetapplication.features.english.data

import com.example.datamodule.models.english.EnglishIrregularCampaignModel

sealed class EnglishIrregularVerbsState {
    class Initial(val callback: () -> Unit) :
        EnglishIrregularVerbsState()

    class Campaign(val campaign: EnglishIrregularCampaignModel) :
        EnglishIrregularVerbsState()

    object Finish : EnglishIrregularVerbsState()

}
