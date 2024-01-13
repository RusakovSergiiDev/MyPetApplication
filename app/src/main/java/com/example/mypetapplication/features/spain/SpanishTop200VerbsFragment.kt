package com.example.mypetapplication.features.spain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.map
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import com.example.mypetapplication.features.spain.compose.SpanishTop200VerbsScreen
import com.example.mypetapplication.features.spain.mappers.SpanishUiMapper

class SpanishTop200VerbsFragment :
    BaseFragment<SpanishTop200VerbsViewModel>(SpanishTop200VerbsViewModel::class.java) {

    private val uiMapper: SpanishUiMapper
        get() = SpanishUiMapper()

    override val screenId: ScreenId
        get() = ScreenId.SpanishTop200Verbs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            SpanishTop200VerbsScreen(
                onBackClicked = { viewModel.onBackClicked() },
                spanishVerbUiItemsState = viewModel.spanishTop200VerbsLiveData.map { items ->
                    uiMapper.mapToUiItems(items)
                }.observeAsState(initial = emptyList())
            )
        }
    }

    override fun onSetupObservers() {

    }
}