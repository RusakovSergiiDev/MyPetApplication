package com.example.mypetapplication.features.asynctest

import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class AsyncTestViewModel @Inject constructor() : BaseContentViewModel<AsyncTestScreenContent>() {

    // Private param(s)
    private val screenContentFlowSource = MutableStateFlow(AsyncTestScreenContent())

    // Base param(s)
    override val screenContentFlow: Flow<AsyncTestScreenContent> = screenContentFlowSource

    init {
        setupTopAppBar(true, R.string.label_asyncTest)

        registerScreenContentSource(screenContentFlow)
    }
}