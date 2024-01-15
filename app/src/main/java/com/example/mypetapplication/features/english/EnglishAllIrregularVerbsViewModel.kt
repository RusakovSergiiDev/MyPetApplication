package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.GetEnglishIrregularVerbsTaskFlowOrLoadUseCase
import com.example.mypetapplication.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnglishAllIrregularVerbsViewModel @Inject constructor(
    private val getEnglishIrregularVerbsTaskFlowOrLoadUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val englishIrregularVerbsTaskSourceFlow =
        MutableStateFlow<Task<List<EnglishIrregularVerbModel>>>(Task.Initial)
    private val englishIrregularVerbsSourceFlow = englishIrregularVerbsTaskSourceFlow.map {
        if (it is Task.Success) it.data
        else emptyList()
    }
    private val englishIrregularVerbsMappedSourceFlow =
        englishIrregularVerbsSourceFlow.map { models ->
            models.forEachIndexed { index, englishIrregularVerbModel ->
                englishIrregularVerbModel.index = index
            }
            models
        }

    // External param(s)
    val englishIrregularVerbUiItemsLiveData: LiveData<List<EnglishIrregularVerbModel>> =
        englishIrregularVerbsMappedSourceFlow.asLiveData()

    init {
        viewModelScope.launch {
            getEnglishIrregularVerbsTaskFlowOrLoadUseCase.execute().collect {
                englishIrregularVerbsTaskSourceFlow.value = it
            }
        }
    }
}