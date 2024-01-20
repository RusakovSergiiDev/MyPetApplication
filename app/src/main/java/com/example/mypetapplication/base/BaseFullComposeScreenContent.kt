package com.example.mypetapplication.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.example.presentationmodule.data.TopAppBarAction

data class BaseFullComposeScreenContent<T : IBaseScreenContent>(
    @StringRes val topAppBarTitleResId: Int,
    val topAppBarAction: LiveData<TopAppBarAction?>,
    val content: LiveData<T>,
)