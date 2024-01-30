package com.example.mypetapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.example.datamodule.types.ScreenId

abstract class BaseFragment<VM : BaseViewModel>(
    private val viewModelJavaClass: Class<VM>
) : Fragment() {

    abstract val screenId: ScreenId?
    abstract fun provideView(): ComposeView
    abstract fun onSetupObservers()

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModelJavaClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return provideView()
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

    protected fun <T : IScreenContent> createCommonComposeScreen(
        fullScreenContentLiveData: LiveData<FullScreenContent<T>>,
        contentScreen: @Composable (State<T?>) -> Unit
    ): ComposeView {
        return ComposeView(requireContext()).apply {
            setContent {
                BaseComposeScreen(
                    isShowGlobalSnackbarError = viewModel.snackbarErrorEvent.observeAsState(initial = null),
                    isShowGlobalLoading = viewModel.isLoadingLiveData.observeAsState(initial = false),
                    onRetryClicked = { viewModel.onRetryClicked() },
                    isShowGlobalRetry = viewModel.isContentInErrorStateLiveData.observeAsState(
                        initial = false
                    ),
                    topAppBarContentLiveData = fullScreenContentLiveData.map { it.topAppBarContent },
                    screenContentLiveData = fullScreenContentLiveData.map { it.screenContent },
                    contentScreen = { contentForScreenState ->
                        contentScreen(contentForScreenState)
                    })
            }
        }
    }

    protected fun <T : IScreenContent> createScreen(
        screenContentLiveData: LiveData<T?>,
        isIgnoreGlobalLoading: Boolean = false,
        contentScreen: @Composable (State<T?>) -> Unit
    ): ComposeView {
        return ComposeView(requireContext()).apply {
            setContent {
                BaseComposeScreen(
                    isShowGlobalSnackbarError = viewModel.snackbarErrorEvent.observeAsState(initial = null),
                    isShowGlobalLoading = if (isIgnoreGlobalLoading) null
                    else viewModel.isLoadingLiveData.observeAsState(initial = false),
                    onRetryClicked = { viewModel.onRetryClicked() },
                    isShowGlobalRetry = viewModel.isContentInErrorStateLiveData.observeAsState(
                        initial = false
                    ),
                    topAppBarContentLiveData = viewModel.topAppBarContentLiveData,
                    screenContentLiveData = screenContentLiveData,
                    contentScreen = { contentForScreenState ->
                        contentScreen(contentForScreenState)
                    })
            }
        }
    }

//    private fun getOnBackClicked(isShowBackAction: Boolean): (() -> Unit)? =
//        if (isShowBackAction) viewModel::onBackClicked else null
}