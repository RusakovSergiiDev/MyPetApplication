package com.example.mypetapplication.authselection

import androidx.compose.ui.platform.ComposeView
import com.example.mypetapplication.authselection.compose.AuthSelectionScreen
import com.example.mypetapplication.base.BaseFragment
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthSelectionFragment :
    BaseFragment<AuthSelectionViewModel>(AuthSelectionViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.AuthenticationSelectionScreen

    override fun provideView(): ComposeView = createCommonComposeScreen(
        viewModel.getScreenContentSource()
    ) { contentState ->
        AuthSelectionScreen(contentState)
    }

    override fun onSetupObservers() {
        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_authSelectionFragment_to_homeFragment)
        }
    }
}