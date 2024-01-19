package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.logicmodule.usecases.english.GetEnglishRulesTaskFlowOrLoadUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishRulesScreenContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@HiltViewModel
class EnglishRulesViewModel @Inject constructor(
    private val getEnglishRulesTaskFlowOrLoadUseCase: GetEnglishRulesTaskFlowOrLoadUseCase,
) : BaseContentViewModel<EnglishRulesScreenContent>() {

    // Internal param(s)
    private val englishRulesSourceFlow = MutableStateFlow<EnglishRulesModel?>(null)

    // Base param(s)
    override val contentWrapperFlow: Flow<EnglishRulesScreenContent> =
        englishRulesSourceFlow.mapNotNull {
            EnglishRulesScreenContent(it)
        }
    override val contentSourceLiveData: LiveData<EnglishRulesScreenContent> =
        contentWrapperFlow.asLiveData()
    override val topAppBarTitleResId: Int
        get() = R.string.label_englishRules

    init {
        executeUseCase(
            useCase = getEnglishRulesTaskFlowOrLoadUseCase,
            onSuccess = { data ->
                englishRulesSourceFlow.value = data
            })
        prepareScreenContentSource()
    }

    override fun onRetryClicked() {
        retryUseCase(getEnglishRulesTaskFlowOrLoadUseCase)
    }
}