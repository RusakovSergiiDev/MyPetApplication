package com.example.mypetapplication.features.spain

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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
                models.forEachIndexed { index, spanishTop200VerbsModel ->
                    spanishTop200VerbsModel.index = index
                }
                spanishUiMapper.mapToUiItems(models)
            } else {
                emptyList()
            }
        }
    private val spanishTop200VerbsContentFlowSource = spanishTop200VerbsMappedFlowSource.map {
        SpanishTop200VerbsScreenContent(it)
    }
    private val contentLiveData: LiveData<SpanishTop200VerbsScreenContent> =
        spanishTop200VerbsContentFlowSource.asLiveData()

    // Base fun(s)
    override fun getTopAppBarTitleResId() = R.string.label_Top200SpanishVerbs
    override fun onRetryClicked() {
        retryUseCase(getSpanishTop200VerbsTaskFlowOrLoadUseCase)
    }

    init {
        executeForTaskResultUseCase(getSpanishTop200VerbsTaskFlowOrLoadUseCase) { task ->
            spanishTop200VerbsTaskFlowSource.value = task
            setTopAppBarAction(task.createRetryTopAppBarAction { onRetryClicked() })
        }
        registerContentSource(contentLiveData)
    }
}