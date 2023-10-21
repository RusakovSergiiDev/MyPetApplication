package com.example.mypetapplication.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mypetapplication.R
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import com.example.mypetapplication.utils.inflateView

class SplashFragment : BaseFragment<SplashViewModel>(SplashViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.Splash

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflateView(R.layout.fragment_splash, container)
    }

    override fun onSetupObservers() {
        viewModel.navigateToAuthSelectionEvent.observe(this.viewLifecycleOwner) {
            findNavController().navigate(R.id.action_splashFragment_to_authSelectionFragment)
        }
        viewModel.navigateToHomeEvent.observe(this.viewLifecycleOwner) {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}