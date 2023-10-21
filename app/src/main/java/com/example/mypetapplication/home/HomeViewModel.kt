package com.example.mypetapplication.home

import androidx.lifecycle.asLiveData
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.home.data.HomeMainOptionItem
import com.example.mypetapplication.home.data.HomeMainOptionType
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : BaseViewModel() {

    // Internal param(s)
    private val homeMainOptionItemsSourceFlow =
        MutableStateFlow<List<HomeMainOptionItem>>(emptyList())

    // External param(s)
    val homeMainOptionsLiveData = homeMainOptionItemsSourceFlow.asLiveData()

    init {
        generateHomeMainOptionItems()
    }

    private fun generateHomeMainOptionItems() {
        val result = mutableListOf<HomeMainOptionItem>()
        val irregularVerbsItem = HomeMainOptionItem(
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

    }
}