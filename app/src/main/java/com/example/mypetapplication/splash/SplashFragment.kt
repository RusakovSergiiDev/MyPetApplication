package com.example.mypetapplication.splash

import androidx.compose.ui.platform.ComposeView
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.splash.compose.SplashScreen
import com.example.mypetapplication.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<SplashViewModel>(SplashViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.SplashScreen

    override fun provideView(): ComposeView = createCommonComposeScreen(
        viewModel.fullScreenContentLiveData
    ) { contentState ->
        SplashScreen(contentState)
    }

    override fun onSetupObservers() {
        viewModel.navigateToAuthSelectionEvent.observe(this.viewLifecycleOwner) {
            navigate(R.id.action_splashFragment_to_authSelectionFragment)
        }
        viewModel.navigateToHomeEvent.observe(this.viewLifecycleOwner) {
            navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}