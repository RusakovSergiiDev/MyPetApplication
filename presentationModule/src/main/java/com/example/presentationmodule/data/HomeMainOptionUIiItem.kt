package com.example.presentationmodule.data


data class HomeMainOptionUIiItem(
    val title: String,
    val description: String? = null,
    val onHomeMainOptionItemClicked: (() -> Unit)? = null
)
