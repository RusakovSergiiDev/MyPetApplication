package com.example.mypetapplication.features.english

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishAllIrregularVerbsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnglishAllIrregularVerbsFragment :
    BaseFragment<EnglishAllIrregularVerbsViewModel>(EnglishAllIrregularVerbsViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishAllIrregularVerbsScreen


    override fun provideView(): ComposeView = createCommonComposeScreen(
        viewModel.getScreenContentSource()
    ) { contentState ->
        EnglishAllIrregularVerbsScreen(contentState)
    }

    override fun onSetupObservers() {

    }
}