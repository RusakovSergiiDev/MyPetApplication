package com.example.mypetapplication.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.datamodule.types.Task
import com.example.datamodule.types.isLoading
import com.example.datamodule.types.isSuccess
import com.example.logicmodule.usecases.GetHomeFeaturesUseCase
import com.example.logicmodule.usecases.firebase.TryToLogOutCase
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.base.TopAppBarNavigationIcon
import com.example.mypetapplication.home.data.HomeScreenContent
import com.example.mypetapplication.home.map.HomeUiMapper
import com.example.presentationmodule.R
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeFeaturesUseCase: GetHomeFeaturesUseCase,
    private val tryToLogOutCase: TryToLogOutCase,
    uiMapper: HomeUiMapper
) : BaseContentViewModel<HomeScreenContent>() {

    // Internal param(s)
    private val homeMainOptionsFlowSource =
        MutableStateFlow<List<HomeMainOptionModel>>(emptyList())
    private val homeMainOptionsMappedFlowSource = homeMainOptionsFlowSource.map { models ->
        HomeScreenContent(
            uiMapper.mapToUiItems(
                models = models,
                callback = { handleHomeMainOptionItemSelection(it) }
            )
        )
    }
    private val logOutProgressFlowSource =
        MutableStateFlow<Task<Unit>>(Task.Initial)

    override val screenContentFlow: Flow<HomeScreenContent> = homeMainOptionsMappedFlowSource

    // Base fun(s)
    override fun onLogOutClicked() {
        viewModelScope.launch {
            tryToLogOutCase.execute { task ->
                logOutProgressFlowSource.value = task
                if (task.isSuccess()) {
                    logOutEvent.call()
                }
            }
        }
    }

    // Event(s)
    val navigateToEnglishRulesEvent = SimpleNavigationEvent()
    val navigateToEnglishIrregularVerbsEvent = SimpleNavigationEvent()
    val navigateToSpanishTop200VerbsEvent = SimpleNavigationEvent()

    init {
        setTopAppBarNavigationIcon(TopAppBarNavigationIcon(
            imageVector = Icons.Default.Menu,
            callback = {}
        ))
        setTopAppBarTitleResId(R.string.label_home)

        registerScreenContentSource(screenContentFlow)

        executeForSuccessTaskResultUseCase(getHomeFeaturesUseCase) {
            homeMainOptionsFlowSource.value = it
        }
        logOutProgressFlowSource.onEach {
            if (it.isLoading()) {
                setTopAppBarAction(
                    TopAppBarAction.Loading
                )
            } else {
                setTopAppBarAction(
                    TopAppBarAction.TextAction(
                        textResId = R.string.label_logOut,
                        callback = { onLogOutClicked() }
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun handleHomeMainOptionItemSelection(type: HomeMainOptionType) {
        when (type) {
            HomeMainOptionType.ENGLISH_RULES -> navigateToEnglishRulesEvent.call()
            HomeMainOptionType.ENGLISH_IRREGULAR_VERBS -> navigateToEnglishIrregularVerbsEvent.call()
            HomeMainOptionType.SPANISH_TOP_200_VERBS -> navigateToSpanishTop200VerbsEvent.call()
            else -> {}
        }
    }
}