package com.example.mypetapplication.features.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishRulesScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class EnglishRulesFragment :
    BaseFragment<EnglishRulesViewModel>(EnglishRulesViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishRulesScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            EnglishRulesScreen(
                onBackClicked = { viewModel.onBackClicked() },
                isLoadingState = viewModel.isContentLoadingLiveData.observeAsState(initial = false),
                onRetryClicked = { viewModel.retry() }
            )
        }
    }

    override fun onSetupObservers() {
        viewModel.showErrorEvent.observe(this) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}