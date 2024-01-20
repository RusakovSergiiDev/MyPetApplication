package com.example.mypetapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.presentationmodule.data.TopAppBarAction

abstract class BaseContentViewModel<T : IBaseScreenContent> : BaseViewModel() {

    // Internal param(s)
    private val titleResIdLiveDataSource = MutableLiveData<Int>()
    private val topAppBarActionLiveDataSource = MutableLiveData<TopAppBarAction?>()
    private val contentLiveDataSource = MutableLiveData<BaseFullComposeScreenContent<T>>()

    abstract fun getTopAppBarTitleResId(): Int

    protected open fun setTopAppBarAction(topAppBarAction: TopAppBarAction?) {
        topAppBarActionLiveDataSource.value = topAppBarAction
    }

    fun registerContentSource(content: LiveData<T>) {
        contentLiveDataSource.value = BaseFullComposeScreenContent(
            topAppBarTitleResId = getTopAppBarTitleResId(),
            topAppBarAction = topAppBarActionLiveDataSource,
            content = content
        )
    }

    fun getScreenContentSource(): LiveData<BaseFullComposeScreenContent<T>> = contentLiveDataSource
}