package com.example.mypetapplication.home

import androidx.lifecycle.asLiveData
import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.logicmodule.usecases.GetHomeFeaturesUseCase
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.home.data.HomeScreenContent
import com.example.mypetapplication.home.map.HomeUiMapper
import com.example.presentationmodule.R
import com.example.mypetapplication.utils.SimpleNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeFeaturesUseCase: GetHomeFeaturesUseCase,
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
    private val contentLiveDataSource = homeMainOptionsMappedFlowSource.asLiveData()

    // Base fun(s)
    override fun getTopAppBarTitleResId() = R.string.label_home

    // Event(s)
    val navigateToEnglishRulesEvent = SimpleNavigationEvent()
    val navigateToEnglishIrregularVerbsEvent = SimpleNavigationEvent()
    val navigateToSpanishTop200VerbsEvent = SimpleNavigationEvent()

    init {
        executeForSuccessTaskResultUseCase(getHomeFeaturesUseCase) {
            homeMainOptionsFlowSource.value = it
        }
        registerContentSource(contentLiveDataSource)
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