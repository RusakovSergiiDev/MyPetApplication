package com.example.mypetapplication.features.english

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishRulesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnglishRulesFragment :
    BaseFragment<EnglishRulesViewModel>(EnglishRulesViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishRulesScreen

    override fun provideView(): ComposeView = createCommonComposeScreen(
        contentLiveData = viewModel.getScreenContentSource(),
        contentScreen = { contentState ->
            EnglishRulesScreen(contentState)
        }
    )

    override fun onSetupObservers() {

    }
}