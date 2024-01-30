package com.example.mypetapplication.features.english

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishItVerbsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnglishItVerbsFragment :
    BaseFragment<EnglishItVerbsViewModel>(EnglishItVerbsViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.SpanishItVerbsScreen

    override fun provideView(): ComposeView =
        createScreen(viewModel.screenContentLiveData) { screenContentState ->
            EnglishItVerbsScreen(screenContentState)
        }

    override fun onSetupObservers() {
        // Nothing
    }
}