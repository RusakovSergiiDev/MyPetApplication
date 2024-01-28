package com.example.mypetapplication.features.english

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.english.GetEnglishIrregularVerbsCampaignTaskFlowUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsState
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsScreenContent
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnglishIrregularVerbsViewModel @Inject constructor(
    private val getEnglishIrregularVerbsCampaignTaskFlowUseCase: GetEnglishIrregularVerbsCampaignTaskFlowUseCase,
) : BaseContentViewModel<EnglishIrregularVerbsScreenContent>() {

    // Internal param(s)
    private val initialState = EnglishIrregularVerbsState.Initial(
        callback = { onCampaignStartClicked() }
    )
    private val finishState = EnglishIrregularVerbsState.Finish(
        callback = { onCampaignRestartClicked() }
    )
    private val currentStateSource = mutableStateOf<EnglishIrregularVerbsState>(initialState)
    private val screenContentFlowSource =
        MutableStateFlow(EnglishIrregularVerbsScreenContent(currentStateSource))

    // Base param(s)
    override val screenContentFlow: Flow<EnglishIrregularVerbsScreenContent> =
        screenContentFlowSource

    // Event(s)
    val navigateToSeeAllEvent = SimpleNavigationEvent()

    init {
        setupTopAppBar(titleResId = R.string.label_englishIrregularVerbs)
        setTopAppBarAction(
            TopAppBarAction.TextAction(
                textResId = R.string.label_seeAll,
                callback = ::onSeeAllClicked
            )
        )

        registerScreenContentSource(screenContentFlow)
    }

    private fun onSeeAllClicked() {
        navigateToSeeAllEvent.call()
    }

    private fun generateAndStartCampaign() {
        executeForTaskResultUseCase(getEnglishIrregularVerbsCampaignTaskFlowUseCase) { task ->
            if (task is Task.Success) {
                val campaignModel =
                    task.data.copy(onMissionCheckCallback = { onMissionCheckCallback(it) })
                val campaignState = EnglishIrregularVerbsState.Campaign(
                    campaign = campaignModel
                )
                currentStateSource.value = campaignState
            }
        }
    }

    private fun onCampaignStartClicked() {
        generateAndStartCampaign()
    }

    private fun onCampaignRestartClicked() {
        generateAndStartCampaign()
    }

    private fun onMissionCheckCallback(currentMission: EnglishIrregularMissionModel) {
        viewModelScope.launch {
            screenContentFlowSource.collect {
                val state = it.state.value
                if (state is EnglishIrregularVerbsState.Campaign) {
                    val campaign = state.campaign
                    val missions = campaign.missions
                    val index = missions.indexOf(currentMission)
                    delay(250)
                    if (index != missions.lastIndex) {
                        campaign.setCurrentMissionIndex(index + 1)
                    } else {
                        currentStateSource.value = finishState
                    }
                }
            }
        }
    }
}