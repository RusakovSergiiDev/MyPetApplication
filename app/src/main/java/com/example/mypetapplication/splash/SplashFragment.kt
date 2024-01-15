package com.example.mypetapplication.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.datamodule.types.ScreenId
import com.example.logicmodule.AnalyticsRepository
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.utils.inflateView
import com.example.mypetapplication.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(SplashViewModel::class.java) {

    @Inject
    lateinit var analyticsRepository: AnalyticsRepository

    override val screenId: ScreenId
        get() = ScreenId.SplashScreen

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        analyticsRepository.screenOpened(screenId)
        return inflateView(R.layout.fragment_splash, container)
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