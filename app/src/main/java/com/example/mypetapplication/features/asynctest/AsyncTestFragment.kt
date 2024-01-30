package com.example.mypetapplication.features.asynctest

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.asynctest.compose.AsyncTestScreen

class AsyncTestFragment : BaseFragment<AsyncTestViewModel>(AsyncTestViewModel::class.java) {

    override val screenId: ScreenId = ScreenId.AsyncTestScreen

    override fun provideView(): ComposeView = createScreen(viewModel.screenContentLiveData) {
        AsyncTestScreen(contentState = it)
    }

    override fun onSetupObservers() {
        // Nothing
    }
}