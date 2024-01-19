package com.example.mypetapplication.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData

data class BaseFullComposeScreenContent<T : IBaseScreenContent>(
    @StringRes val topAppBarTitleResId: Int,
    val screenContent: LiveData<T>,
)