package com.example.mypetapplication.home

import androidx.lifecycle.asLiveData
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.data.HomeMainOptionDto
import com.example.mypetapplication.data.HomeMainOptionType
import com.example.mypetapplication.utils.SimpleNavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : BaseViewModel() {

    // Internal param(s)
    private val homeMainOptionItemsSourceFlow =
        MutableStateFlow<List<HomeMainOptionDto>>(emptyList())

    // External param(s)
    val homeMainOptionsLiveData = homeMainOptionItemsSourceFlow.asLiveData()

    // Event(s)
    val navigateToEnglishIrregularVerbs = SimpleNavigationEvent()

    init {
        generateHomeMainOptionItems()
    }

    private fun generateHomeMainOptionItems() {
        val result = mutableListOf<HomeMainOptionDto>()
        val irregularVerbsItem = HomeMainOptionDto(
            type = HomeMainOptionType.IrregularVerbs,
            titleResId = R.string.label_irregularVerbs,
            descriptionResId = R.string.label_irregularVerbsDescription,
            solidColorResId = R.color.colorYellowLight,
            onHomeMainOptionItemClicked = { processHomeMainOptionItemSelection(it) }
        )
        result.add(
            irregularVerbsItem
        )
        homeMainOptionItemsSourceFlow.value = result
    }

    private fun processHomeMainOptionItemSelection(type: HomeMainOptionType) {
        when (type) {
            HomeMainOptionType.IrregularVerbs -> navigateToEnglishIrregularVerbs.call()
            else -> {}
        }
    }
}