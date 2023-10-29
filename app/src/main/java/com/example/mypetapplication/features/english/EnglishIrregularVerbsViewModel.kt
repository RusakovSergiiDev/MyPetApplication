package com.example.mypetapplication.features.english

import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent

class EnglishIrregularVerbsViewModel : BaseViewModel() {

    // Event(s)
    val navigateToSeeAll = SimpleNavigationEvent()

    fun onSeeAllClicked() {
        navigateToSeeAll.call()
    }
}