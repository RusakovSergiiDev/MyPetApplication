package com.example.mypetapplication.features.english

import com.example.datamodule.models.english.EnglishRulesModel
import com.example.logicmodule.usecases.english.GetEnglishRulesTaskFlowOrLoadUseCase
import com.example.mypetapplication.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnglishRulesViewModel @Inject constructor(
    private val getEnglishRulesTaskFlowOrLoadUseCase: GetEnglishRulesTaskFlowOrLoadUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val englishRulesSourceFlow = MutableStateFlow<EnglishRulesModel?>(null)

    init {
        executeUseCase(
            useCase = getEnglishRulesTaskFlowOrLoadUseCase,
            onSuccess = { data ->
                englishRulesSourceFlow.value = data
            })
    }

    fun retry() {
        retryUseCase(getEnglishRulesTaskFlowOrLoadUseCase)
    }
}