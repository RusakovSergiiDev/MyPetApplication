package com.example.mypetapplication.features.english

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.features.english.compose.EnglishRulesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnglishRulesFragment :
    BaseFragment<EnglishRulesViewModel>(EnglishRulesViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.EnglishRulesScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createCommonComposeScreen(
        contentLiveData = viewModel.screenContentLiveData,
        content = { liveData ->
            EnglishRulesScreen(liveData)
        }
    )

    override fun onSetupObservers() {
//        viewModel.showErrorEvent.observe(this) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
    }
}