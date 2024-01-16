package com.example.mypetapplication.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.mypetapplication.base.BaseViewModel
import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.presentationmodule.R
import com.example.mypetapplication.utils.SimpleNavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : BaseViewModel() {

    // Internal param(s)
    private val homeMainOptionItemsSourceFlow =
        MutableStateFlow<List<HomeMainOptionModel>>(emptyList())

    // External param(s)
    val homeMainOptionsLiveData: LiveData<List<HomeMainOptionModel>> =
        homeMainOptionItemsSourceFlow.asLiveData()

    // Event(s)
    val navigateToEnglishRulesEvent = SimpleNavigationEvent()
    val navigateToEnglishIrregularVerbsEvent = SimpleNavigationEvent()
    val navigateToSpanishTop200VerbsEvent = SimpleNavigationEvent()

    init {
        generateHomeMainOptionItems()
    }

    private fun generateHomeMainOptionItems() {
        val result = mutableListOf<HomeMainOptionModel>()
        val englishRulesItem = HomeMainOptionModel(
            type = HomeMainOptionType.ENGLISH_RULES,
            titleResId = R.string.label_englishRules,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(englishRulesItem)
        val englishIrregularVerbsItem = HomeMainOptionModel(
            type = HomeMainOptionType.ENGLISH_IRREGULAR_VERBS,
            titleResId = R.string.label_irregularVerbs,
            descriptionResId = R.string.label_irregularVerbsDescription,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(englishIrregularVerbsItem)
        val spainVerbsItem = HomeMainOptionModel(
            type = HomeMainOptionType.SPANISH_TOP_200_VERBS,
            titleResId = R.string.label_homeOptionSpanishVerbs,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(spainVerbsItem)
        homeMainOptionItemsSourceFlow.value = result
    }

    private fun processHomeMainOptionItemSelection(type: HomeMainOptionType) {
        when (type) {
            HomeMainOptionType.ENGLISH_RULES -> navigateToEnglishRulesEvent.call()
            HomeMainOptionType.ENGLISH_IRREGULAR_VERBS -> navigateToEnglishIrregularVerbsEvent.call()
            HomeMainOptionType.SPANISH_TOP_200_VERBS -> navigateToSpanishTop200VerbsEvent.call()
            else -> {}
        }
    }
}