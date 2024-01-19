package com.example.mypetapplication.base

import androidx.lifecycle.LiveData

data class BaseLocalComposeScreenContent<T : IBaseScreenContent>(
    val screenContent: LiveData<T>
)