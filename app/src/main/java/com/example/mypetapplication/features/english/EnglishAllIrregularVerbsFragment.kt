package com.example.mypetapplication.features.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.map
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import com.example.mypetapplication.features.english.compose.EnglishAllIrregularVerbsScreen
import com.example.mypetapplication.features.english.map.EnglishUiMapper

class EnglishAllIrregularVerbsFragment :
    BaseFragment<EnglishAllIrregularVerbsViewModel>(EnglishAllIrregularVerbsViewModel::class.java) {

    private val uiMapper: EnglishUiMapper
        get() = EnglishUiMapper(requireContext())

    override val screenId: ScreenId
        get() = ScreenId.EnglishAllIrregularVerbs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            EnglishAllIrregularVerbsScreen(
                viewModel.englishIrregularVerbsLiveData.map { items ->
                    uiMapper.mapToUiItems(items)
                }.observeAsState(initial = emptyList())
            )
        }
    }

    override fun onSetupObservers() {

    }
}