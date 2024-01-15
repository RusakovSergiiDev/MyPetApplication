package com.example.mypetapplication.authselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.mypetapplication.authselection.compose.AuthSelectionScreen
import com.example.mypetapplication.base.BaseFragment
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId
import com.example.mypetapplication.R
import com.example.presentationmodule.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthSelectionFragment :
    BaseFragment<AuthSelectionViewModel>(AuthSelectionViewModel::class.java) {

    override val screenId: ScreenId
        get() = ScreenId.AuthenticationSelectionScreen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AuthSelectionScreen(
                        isSignInState = viewModel.isSignInStateLiveData.observeAsState(initial = true),
                        emailState = viewModel.emailLiveData.observeAsState(initial = ""),
                        passwordState = viewModel.passwordLiveData.observeAsState(initial = ""),
                        isSignInEnableState = viewModel.isButtonEnableLiveData.observeAsState(
                            initial = false
                        ),
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
    }

    override fun onSetupObservers() {
        viewModel.navigateToHomeEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_authSelectionFragment_to_homeFragment)
        }
    }
}