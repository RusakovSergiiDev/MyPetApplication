package com.example.mypetapplication.features.spain

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.firebase.GetSpanishTop200VerbsTaskFlowOrLoadUseCase
import com.example.mypetapplication.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpanishTop200VerbsViewModel @Inject constructor(
    private val getSpanishTop200VerbsTaskFlowOrLoadUseCase: GetSpanishTop200VerbsTaskFlowOrLoadUseCase
) : BaseViewModel() {

    // Internal param(s)
    private val spanishTop200VerbsTaskSourceFlow =
        MutableStateFlow<Task<List<SpanishVerbModel>>>(Task.Initial)
    private val spanishTop200VerbsSourceFlow = spanishTop200VerbsTaskSourceFlow.map {
        if (it is Task.Success) it.data
        else emptyList()
    }
    private val spanishTop200VerbsMappedSourceFlow = spanishTop200VerbsSourceFlow
        .map { models ->
            models.forEachIndexed { index, spanishTop200VerbsModel ->
                spanishTop200VerbsModel.index = index
            }
            models
        }

    // External param(s)
    val spanishTop200VerbsLiveData = spanishTop200VerbsMappedSourceFlow.asLiveData()

    init {
        viewModelScope.launch {
            getSpanishTop200VerbsTaskFlowOrLoadUseCase.execute().collect {
                spanishTop200VerbsTaskSourceFlow.value = it
            }
        }
    }
}