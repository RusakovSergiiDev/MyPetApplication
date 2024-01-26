package com.example.mypetapplication.base

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.presentationmodule.data.TopAppBarAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseContentViewModel<T : IScreenContent> : BaseViewModel() {

    // Internal param(s)
    // TopAppBar Part
    private val isShowTopAppBarStateFlowSource = MutableStateFlow(true)
    private val topAppBarNavigationIconStateFlowSource =
        MutableStateFlow<TopAppBarNavigationIcon?>(null)
    private val topAppBarTitleResIdStateFlowSource = MutableStateFlow<Int?>(null)
    private val topAppBarActionsStateFlowSource =
        MutableStateFlow<List<TopAppBarAction>>(emptyList())
    private val topAppBarContentFlow = combine(
        isShowTopAppBarStateFlowSource,
        topAppBarNavigationIconStateFlowSource,
        topAppBarTitleResIdStateFlowSource,
        topAppBarActionsStateFlowSource
    ) { isShow, navigationIcon, titleResId, actions ->
        TopAppBarContent(
            isShow,
            navigationIcon,
            titleResId,
            actions
        )
    }

    // Screen Content Part
    private val screenContentFlowSource = MutableStateFlow<T?>(null)
    abstract val screenContentFlow: Flow<T>

    // Full Screen Content Part
    private val fullScreenContentFlow =
        combine(
            topAppBarContentFlow.filterNotNull(),
            screenContentFlowSource,
        ) { topAppBar, screenContent ->
            FullScreenContent(
                topAppBar,
                screenContent
            )
        }

    // External param(s)
    override val topAppBarContentLiveData: LiveData<TopAppBarContent> =
        topAppBarContentFlow.filterNotNull().asLiveData()
    val screenContentLiveData: LiveData<T?> =
        screenContentFlowSource.asLiveData()
    val fullScreenContentLiveData: LiveData<FullScreenContent<T>> =
        fullScreenContentFlow.filterNotNull().asLiveData()

    protected open fun setIsShowTopAppBar(isShow: Boolean) {
        isShowTopAppBarStateFlowSource.value = isShow
    }

    protected open fun setTopAppBarNavigationIcon(navigationIcon: TopAppBarNavigationIcon) {
        topAppBarNavigationIconStateFlowSource.value = navigationIcon
    }

    protected open fun setTopAppBarTitleResId(@StringRes titleResId: Int, isShowTopBar: Boolean = true) {
        topAppBarTitleResIdStateFlowSource.value = titleResId
        isShowTopAppBarStateFlowSource.value = isShowTopBar
    }

    protected open fun setTopAppBarAction(action: TopAppBarAction) {
        topAppBarActionsStateFlowSource.value = listOf(action)
    }

    protected open fun setupTopAppBar(
        isShowOnBackClick: Boolean = true,
        @StringRes titleResId: Int,
    ) {
        setIsShowTopAppBar(true)
        if (isShowOnBackClick) {
            setTopAppBarNavigationIcon(
                TopAppBarNavigationIcon(
                    imageVector = Icons.Default.ArrowBack,
                    callback = { onBackClicked() }
                )
            )
        }
        setTopAppBarTitleResId(titleResId)
    }

    protected fun registerScreenContentSource(flow: Flow<T>) {
        flow.onEach {
            screenContentFlowSource.value = it
        }.launchIn(viewModelScope)
    }
}