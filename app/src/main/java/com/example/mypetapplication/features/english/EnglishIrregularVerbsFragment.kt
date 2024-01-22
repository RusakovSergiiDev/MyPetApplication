package com.example.mypetapplication.features.english

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishIrregularVerbsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnglishIrregularVerbsFragment :
    BaseFragment<EnglishIrregularVerbsViewModel>(EnglishIrregularVerbsViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishIrregularVerbsScreen

    override fun provideView(): ComposeView = createScreen(
        viewModel.screenContentLiveData,
    ) { contentState ->
        EnglishIrregularVerbsScreen(contentState)
    }

    override fun onSetupObservers() {
        viewModel.navigateToSeeAllEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_englishIrregularVerbsFragment_to_englishAllIrregularVerbsFragment)
        }
    }
}