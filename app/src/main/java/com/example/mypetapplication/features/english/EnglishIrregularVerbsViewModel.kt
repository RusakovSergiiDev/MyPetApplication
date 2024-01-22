package com.example.mypetapplication.features.english

import androidx.compose.runtime.IntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.english.EnglishIrregularCampaignModel
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.logicmodule.usecases.english.GetEnglishIrregularVerbsCampaignTaskFlowUseCase
import com.example.logicmodule.usecases.firebase.GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsScreenContent
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnglishIrregularVerbsViewModel @Inject constructor(
    getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase,
    getEnglishIrregularVerbsCampaignTaskFlowUseCase: GetEnglishIrregularVerbsCampaignTaskFlowUseCase,
) : BaseContentViewModel<EnglishIrregularVerbsScreenContent>() {

    // Internal param(s)
    private val currentMissionIndexStateSource = mutableIntStateOf(0)
    private val currentMissionIndexState: IntState = currentMissionIndexStateSource
    private val englishIrregularCampaignFlowSource =
        MutableStateFlow<EnglishIrregularCampaignModel?>(null)
    private val screenContentFlowSource =
        englishIrregularCampaignFlowSource
            .filterNotNull()
            .map { campaignModel ->
                EnglishIrregularVerbsScreenContent(
                    campaign = campaignModel,
                    currentMissionIndex = currentMissionIndexState,
                    onMissionCheckCallback = { mission -> onMissionCheckCallback(mission) }
                )
            }

    // Event(s)
    val navigateToSeeAllEvent = SimpleNavigationEvent()

    override val screenContentFlow: Flow<EnglishIrregularVerbsScreenContent> =
        screenContentFlowSource

    init {
        setupTopAppBar(titleResId = R.string.label_englishIrregularVerbs)
        setTopAppBarAction(
            TopAppBarAction.TextAction(
                textResId = R.string.label_seeAll,
                callback = ::onSeeAllClicked
            )
        )

        registerScreenContentSource(screenContentFlow)

        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsCampaignTaskFlowUseCase) {
            englishIrregularCampaignFlowSource.value = it
        }
    }

    private fun onSeeAllClicked() {
        navigateToSeeAllEvent.call()
    }

    private fun onMissionCheckCallback(currentMission: EnglishIrregularMissionModel) {
        viewModelScope.launch {
            screenContentFlowSource.collect {
                val missions = it.campaign.missions
                val index = missions.indexOf(currentMission)
                delay(250)
                if (index != missions.lastIndex) {
                    currentMissionIndexStateSource.intValue = index + 1
                } else {

                }
            }
        }
    }
}