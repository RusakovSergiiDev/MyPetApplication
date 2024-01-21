package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.firebase.GetEnglishRulesTaskFlowOrLoadFromFBUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishRulesScreenContent
import com.example.mypetapplication.features.english.mappers.EnglishUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EnglishRulesViewModel @Inject constructor(
    private val getEnglishRulesTaskFlowOrLoadFromFBUseCase: GetEnglishRulesTaskFlowOrLoadFromFBUseCase,
    private val uiMapper: EnglishUiMapper
) : BaseContentViewModel<EnglishRulesScreenContent>() {

    // Internal param(s)
    private val englishRulesFlowSource = MutableStateFlow<Task<EnglishRulesModel>>(Task.Initial)
    private val englishRulesScreenContentFlowSource: Flow<EnglishRulesScreenContent> =
        englishRulesFlowSource.map { task ->
            if (task is Task.Success) {
                EnglishRulesScreenContent(uiMapper.mapToUiModel(task.data))
            } else {
                EnglishRulesScreenContent(
                    uiMapper.mapToUiModel(
                        EnglishRulesModel(emptyList(), emptyList())
                    )
                )
            }
        }
    override val screenContentFlow: Flow<EnglishRulesScreenContent> =
        englishRulesScreenContentFlowSource

    // Base fun(s)
    override fun onRetryClicked() {
        retryUseCase(getEnglishRulesTaskFlowOrLoadFromFBUseCase)
    }

    init {
        setupTopAppBar(titleResId = R.string.label_englishRules)

        executeForTaskResultUseCase(getEnglishRulesTaskFlowOrLoadFromFBUseCase) { task ->
            englishRulesFlowSource.value = task
            task.createRetryTopAppBarAction { onRetryClicked() }?.let { action ->
                setTopAppBarAction(action)
            }
        }
    }
}