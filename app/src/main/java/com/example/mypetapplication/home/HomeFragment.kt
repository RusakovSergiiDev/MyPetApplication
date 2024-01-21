package com.example.mypetapplication.home

import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.home.compose.HomeScreen
import com.example.mypetapplication.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.HomeScreen

    override fun provideView() = createCommonComposeScreen(
        contentLiveData = viewModel.getScreenContentSource(),
        isShowBackAction = false
    ) { contentState ->
        HomeScreen(contentState)
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
        viewModel.logOutEvent.observe(viewLifecycleOwner) {
            navigate(R.id.action_homeFragment_to_authSelectionFragment)
        }
    }
}