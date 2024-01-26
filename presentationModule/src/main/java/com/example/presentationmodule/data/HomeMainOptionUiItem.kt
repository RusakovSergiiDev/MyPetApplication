package com.example.presentationmodule.data


data class HomeMainOptionUiItem(
    val title: String,
    val description: String? = null,
    val callback: (() -> Unit)? = null
)
