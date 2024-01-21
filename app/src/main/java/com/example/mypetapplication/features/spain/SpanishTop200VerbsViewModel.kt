package com.example.mypetapplication.features.spain

import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.firebase.GetSpanishTop200VerbsTaskFlowOrLoadFlowTaskUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.spain.mappers.SpanishUiMapper
import com.example.presentationmodule.data.SpanishVerbUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SpanishTop200VerbsViewModel @Inject constructor(
    private val getSpanishTop200VerbsTaskFlowOrLoadUseCase: GetSpanishTop200VerbsTaskFlowOrLoadFlowTaskUseCase,
    spanishUiMapper: SpanishUiMapper
) : BaseContentViewModel<SpanishTop200VerbsScreenContent>() {

    // Internal param(s)
    private val spanishTop200VerbsTaskFlowSource =
        MutableStateFlow<Task<List<SpanishVerbModel>>>(Task.Initial)
    private val spanishTop200VerbsMappedFlowSource: Flow<List<SpanishVerbUiItem>> =
        spanishTop200VerbsTaskFlowSource.map { task ->
            if (task is Task.Success) {
                val models = task.data
                spanishUiMapper.mapToUiItems(models)
            } else {
                emptyList()
            }
        }
    private val spanishTop200VerbsContentFlowSource = spanishTop200VerbsMappedFlowSource.map {
        SpanishTop200VerbsScreenContent(it)
    }
    override val screenContentFlow: Flow<SpanishTop200VerbsScreenContent> =
        spanishTop200VerbsContentFlowSource

    // Base fun(s)
    override fun onRetryClicked() {
        retryUseCase(getSpanishTop200VerbsTaskFlowOrLoadUseCase)
    }

    init {
        setupTopAppBar(titleResId = R.string.label_Top200SpanishVerbs)

        registerScreenContentSource(screenContentFlow)

        executeForTaskResultUseCase(getSpanishTop200VerbsTaskFlowOrLoadUseCase) { task ->
            spanishTop200VerbsTaskFlowSource.value = task
            task.createRetryTopAppBarAction { onRetryClicked() }?.let { action ->
                setTopAppBarAction(action)
            }
        }
    }
}