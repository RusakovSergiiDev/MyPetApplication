package com.example.mypetapplication.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.map
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.home.compose.HomeScreen
import com.example.mypetapplication.home.map.HomeUiMapper
import com.example.mypetapplication.utils.navigate
import com.example.presentationmodule.AppTheme

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java) {

    private val uiMapper: HomeUiMapper
        get() = HomeUiMapper(requireContext())

    override val screenId: ScreenId
        get() = ScreenId.HomeScreen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(this.requireContext()).apply {
        setContent {
            AppTheme {
                HomeScreen(
                    homeMainOptionUiItemsState = viewModel.homeMainOptionsLiveData.map { items ->
                        uiMapper.mapToUiItems(items)
                    }.observeAsState(initial = emptyList()),
                    onLogOutClicked = { viewModel.onLogOutClicked() }
                )
            }
        }
    }

    override fun onSetupObservers() {
        viewModel.navigateToEnglishRulesEvent.observe(viewLifecycleOwner) {
            navigate(R.id.action_homeFragment_to_englishRulesFragment)
        }
        viewModel.navigateToEnglishIrregularVerbsEvent.observe(viewLifecycleOwner) {
            navigate(R.id.action_homeFragment_to_englishIrregularVerbsFragment)
        }
        viewModel.navigateToSpanishTop200VerbsEvent.observe(viewLifecycleOwner) {
            navigate(R.id.action_homeFragment_to_spanishTop200VerbsFragment)
        }
    }
}