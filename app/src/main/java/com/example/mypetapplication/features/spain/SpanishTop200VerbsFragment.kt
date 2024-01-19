package com.example.mypetapplication.features.spain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.spain.compose.SpanishTop200VerbsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpanishTop200VerbsFragment :
    BaseFragment<SpanishTop200VerbsViewModel>(SpanishTop200VerbsViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.SpanishTop200VerbsScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createCommonComposeScreen(
        contentLiveData = viewModel.screenContentLiveData,
        content = { contentLiveData ->
            SpanishTop200VerbsScreen(contentLiveData)
        }
    )

    override fun onSetupObservers() {
    }
}