package com.example.mypetapplication.features.english.data

import com.example.logicmodule.data.EnglishIrregularCampaignModel

sealed class EnglishIrregularVerbsState {
    class Initial(val callback: () -> Unit) : EnglishIrregularVerbsState()

    class Campaign(val campaign: EnglishIrregularCampaignModel) : EnglishIrregularVerbsState()

    class Finish(val callback: () -> Unit) : EnglishIrregularVerbsState()

}
