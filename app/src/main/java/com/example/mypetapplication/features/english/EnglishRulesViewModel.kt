package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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
import kotlinx.coroutines.flow.map
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
    private val contentLiveData: LiveData<EnglishRulesScreenContent> =
        englishRulesScreenContentFlowSource.asLiveData()

    // Base fun(s)
    override fun getTopAppBarTitleResId() = R.string.label_englishRules
    override fun onRetryClicked() {
        retryUseCase(getEnglishRulesTaskFlowOrLoadFromFBUseCase)
    }

    init {
        executeForTaskResultUseCase(getEnglishRulesTaskFlowOrLoadFromFBUseCase) { task ->
            englishRulesFlowSource.value = task
            setTopAppBarAction(task.createRetryTopAppBarAction { onRetryClicked() })
        }
        registerContentSource(contentLiveData)
    }
}