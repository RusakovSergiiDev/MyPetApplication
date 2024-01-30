package com.example.mypetapplication.features.spain

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.spain.compose.SpanishTop200VerbsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpanishTop200VerbsFragment :
    BaseFragment<SpanishTop200VerbsViewModel>(
        SpanishTop200VerbsViewModel::class.java,
    ) {

    override val screenId: ScreenId
        get() = ScreenId.SpanishTop200VerbsScreen

    override fun provideView(): ComposeView = createScreen(
        viewModel.screenContentLiveData
    ) { contentState ->
        SpanishTop200VerbsScreen(contentState)
    }

    override fun onSetupObservers() {
        // Nothing
    }
}