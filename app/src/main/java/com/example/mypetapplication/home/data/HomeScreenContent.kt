package com.example.mypetapplication.home.data

import com.example.mypetapplication.base.IScreenContent
import com.example.presentationmodule.data.HomeMainOptionUiItem

data class HomeScreenContent(
    val homeOptions: List<HomeMainOptionUiItem>
) : IScreenContent