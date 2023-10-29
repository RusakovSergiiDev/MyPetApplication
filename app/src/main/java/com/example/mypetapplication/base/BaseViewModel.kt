package com.example.mypetapplication.base

import androidx.lifecycle.ViewModel
import com.example.mypetapplication.utils.SimpleNavigationEvent

open class BaseViewModel : ViewModel() {

    val navigationBackEvent = SimpleNavigationEvent()

    fun onBackClicked() {
        navigationBackEvent.call()
    }
}