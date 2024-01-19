package com.example.mypetapplication.base

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId

abstract class BaseFragment<VM : BaseViewModel>(
    private val viewModelJavaClass: Class<VM>
) : Fragment() {

    abstract val screenId: ScreenId?

    abstract fun onSetupObservers()

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelJavaClass]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupObservers()

        viewModel.navigationBackEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        viewModel.logOutEvent.observe(viewLifecycleOwner) {
            // navigate(R.navigation.auth_navigation)
        }
    }

    protected fun <T : IBaseScreenContent> createCommonComposeScreen(
        contentLiveData: LiveData<BaseFullComposeScreenContent<T>>,
        content: @Composable (LiveData<BaseFullComposeScreenContent<T>>) -> Unit
    ): ComposeView {
        return ComposeView(requireContext()).apply {
            setContent {
                BaseComposeScreen(
                    onBackClicked = { viewModel.onBackClicked() },
                    onRetryClicked = { viewModel.onRetryClicked() },
                    isShowLoading = viewModel.isLoadingLiveData,
                    isShowRetry = viewModel.isContentInErrorStateLiveData,
                    contentLiveData = contentLiveData,
                    content = { source ->
                        content(source)
                    })
            }
        }
    }
}