package com.example.mypetapplication.authselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.mypetapplication.authselection.compose.AuthSelectionScreen
import com.example.mypetapplication.base.BaseFragment
import com.example.mypetapplication.base.ScreenId
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.fragment.findNavController
import com.example.mypetapplication.R
import com.example.mypetapplication.splash.SplashViewModel

class AuthSelectionFragment :
    BaseFragment<AuthSelectionViewModel>(AuthSelectionViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.AuthenticationSelection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AuthSelectionScreen(
                    isSignInState = viewModel.isSignInLiveData.observeAsState(initial = true),
                    emailState = viewModel.emailLiveData.observeAsState(initial = ""),
                    passwordState = viewModel.passwordLiveData.observeAsState(initial = ""),
                    isSignInEnableState = viewModel.isSignInEnableLiveData.observeAsState(initial = false),
                    onEmailChanged = { viewModel.onEmailChanged(it) },
                    onPasswordChanged = { viewModel.onPasswordChanged(it) },
                    onSwitchToSignIn = { viewModel.switchToSignIn() },
                    onSwitchToSignUp = { viewModel.switchToSignUp() },
                    onSignInClicked = { viewModel.signIn() },
                    onSignUpClicked = { viewModel.signUp() },
                )
            }
        }
    }

    override fun onSetupObservers() {
        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_authSelectionFragment_to_homeFragment)
        }
    }
}