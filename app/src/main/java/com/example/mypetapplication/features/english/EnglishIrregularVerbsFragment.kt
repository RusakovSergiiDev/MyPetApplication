package com.example.mypetapplication.features.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishIrregularVerbsScreen

class EnglishIrregularVerbsFragment :
    BaseFragment<EnglishIrregularVerbsViewModel>(EnglishIrregularVerbsViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishIrregularVerbsScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            EnglishIrregularVerbsScreen(
                onBackClicked = { viewModel.onBackClicked() },
                onSeeAllClicked = { viewModel.onSeeAllClicked() }
            )
        }
    }

    override fun onSetupObservers() {
        viewModel.navigateToSeeAll.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_englishIrregularVerbsFragment_to_englishAllIrregularVerbsFragment)
        }
    }
}