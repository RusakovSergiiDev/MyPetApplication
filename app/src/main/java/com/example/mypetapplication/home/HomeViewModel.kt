package com.example.mypetapplication.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.mypetapplication.base.BaseViewModel
import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.mypetapplication.utils.SimpleNavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : BaseViewModel() {

    // Internal param(s)
    private val homeMainOptionItemsSourceFlow =
        MutableStateFlow<List<HomeMainOptionModel>>(emptyList())

    // External param(s)
    val homeMainOptionsLiveData: LiveData<List<HomeMainOptionModel>> = homeMainOptionItemsSourceFlow.asLiveData()

    // Event(s)
    val navigateToEnglishIrregularVerbs = SimpleNavigationEvent()
    val navigateToSpanishTop200Verbs = SimpleNavigationEvent()

    init {
        generateHomeMainOptionItems()
    }

    private fun generateHomeMainOptionItems() {
        val result = mutableListOf<HomeMainOptionModel>()
        val englishIrregularVerbsItem = HomeMainOptionModel(
            type = HomeMainOptionType.ENGLISH_IRREGULAR_VERBS,
            titleResId = com.example.presentationmodule.R.string.label_irregularVerbs,
            descriptionResId = com.example.presentationmodule.R.string.label_irregularVerbsDescription,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(
            englishIrregularVerbsItem
        )
        val spainVerbsItem = HomeMainOptionModel(
            type = HomeMainOptionType.SPANISH_TOP_200_VERBS,
            titleResId = com.example.presentationmodule.R.string.label_homeOptionSpanishVerbs,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(spainVerbsItem)
        homeMainOptionItemsSourceFlow.value = result
    }

    private fun processHomeMainOptionItemSelection(type: HomeMainOptionType) {
        when (type) {
            HomeMainOptionType.ENGLISH_IRREGULAR_VERBS -> navigateToEnglishIrregularVerbs.call()
            HomeMainOptionType.SPANISH_TOP_200_VERBS -> navigateToSpanishTop200Verbs.call()
            else -> {}
        }
    }
}