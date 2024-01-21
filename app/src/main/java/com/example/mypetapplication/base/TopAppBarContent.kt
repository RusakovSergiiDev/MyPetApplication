package com.example.mypetapplication.base

import com.example.presentationmodule.data.TopAppBarAction

data class TopAppBarContent(
    val isShow: Boolean = true,
    val navigationIcon: TopAppBarNavigationIcon?,
    val titleResId: Int?,
    val actions: List<TopAppBarAction> = emptyList()
)