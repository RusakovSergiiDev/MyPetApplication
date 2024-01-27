package com.example.mypetapplication.features.english

import androidx.compose.runtime.IntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.english.EnglishIrregularCampaignModel
import com.example.datamodule.models.english.EnglishIrregularMissionModel
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
        callback = { onMissionStartClicked() }
    )
    private val stateSource = mutableStateOf<EnglishIrregularVerbsState>(initialState)
    private val currentMissionIndexStateSource = mutableIntStateOf(0)
    private val currentMissionIndexState: IntState = currentMissionIndexStateSource
    private val screenContentFlowSource = MutableStateFlow(EnglishIrregularVerbsScreenContent(stateSource))

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
    }

    private fun onSeeAllClicked() {
        navigateToSeeAllEvent.call()
    }

    private fun onMissionStartClicked() {
        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsCampaignTaskFlowUseCase) {missions ->
            val campaign = EnglishIrregularCampaignModel(
                missions = missions,
                currentMissionIndex = currentMissionIndexState,
                onMissionCheckCallback = { mission -> onMissionCheckCallback(mission) }
            )
            val campaignState = EnglishIrregularVerbsState.Campaign(
                campaign = campaign
            )
            stateSource.value = campaignState
        }
    }

    private fun onMissionCheckCallback(currentMission: EnglishIrregularMissionModel) {
        viewModelScope.launch {
            screenContentFlowSource.collect {
                val state = it.state.value
                if (state is EnglishIrregularVerbsState.Campaign) {
                    val missions = state.campaign.missions
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
}