package com.example.mypetapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

abstract class BaseContentViewModel<T : IBaseScreenContent> : BaseViewModel() {

    abstract val contentWrapperFlow: Flow<IBaseScreenContent>
    abstract val contentSourceLiveData: LiveData<T>
    abstract val topAppBarTitleResId: Int

    // Internal param(s)
    private val screenContentSourceLiveData = MutableLiveData<BaseFullComposeScreenContent<T>>()

    // External param(s)
    val screenContentLiveData: LiveData<BaseFullComposeScreenContent<T>> =
        screenContentSourceLiveData

    fun prepareScreenContentSource() {
        val content = BaseFullComposeScreenContent(
            topAppBarTitleResId = topAppBarTitleResId,
            screenContent = contentSourceLiveData,
        )
        screenContentSourceLiveData.value = content
    }
}