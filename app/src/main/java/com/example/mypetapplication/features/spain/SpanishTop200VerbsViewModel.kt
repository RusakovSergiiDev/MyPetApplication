package com.example.mypetapplication.features.spain

import androidx.lifecycle.asLiveData
import com.example.datamodule.models.SpanishVerbModel
import com.example.logicmodule.usecases.firebase.GetSpanishTop200VerbsTaskFlowOrLoadFlowTaskUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.spain.mappers.SpanishUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SpanishTop200VerbsViewModel @Inject constructor(
    private val getSpanishTop200VerbsTaskFlowOrLoadUseCase: GetSpanishTop200VerbsTaskFlowOrLoadFlowTaskUseCase,
    spanishUiMapper: SpanishUiMapper
) : BaseContentViewModel<SpanishTop200VerbsScreenContent>() {

    // Internal param(s)
    private val spanishTop200VerbsSourceFlow = MutableStateFlow<List<SpanishVerbModel>>(emptyList())
    private val spanishTop200VerbsMappedSourceFlow = spanishTop200VerbsSourceFlow
        .map { models ->
            models.forEachIndexed { index, spanishTop200VerbsModel ->
                spanishTop200VerbsModel.index = index
            }
            spanishUiMapper.mapToUiItems(models)
        }

    // Base param(s)
    override val contentWrapperFlow = spanishTop200VerbsMappedSourceFlow.map {
        SpanishTop200VerbsScreenContent(it)
    }
    override val contentSourceLiveData = contentWrapperFlow.asLiveData()
    override val topAppBarTitleResId: Int
        get() = R.string.label_Top200SpanishVerbs

    init {
        executeUseCase(
            useCase = getSpanishTop200VerbsTaskFlowOrLoadUseCase,
            onSuccess = {
                spanishTop200VerbsSourceFlow.value = it
            })
        prepareScreenContentSource()
    }

    fun retry() {
        retryUseCase(getSpanishTop200VerbsTaskFlowOrLoadUseCase)
    }
}