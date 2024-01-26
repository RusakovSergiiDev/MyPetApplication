package com.example.mypetapplication.features.english

import com.example.datamodule.models.english.EnglishItVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.usecases.firebase.GetEnglishItVerbsTaskFlowOrLoadFromFBUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishItVerbsScreenContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnglishItVerbsViewModel @Inject constructor(
    getEnglishItVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishItVerbsTaskFlowOrLoadFromFBUseCase
) :
    BaseContentViewModel<EnglishItVerbsScreenContent>() {

    // Internal param(s)
    private val englishItVerbsTaskFlowSource =
        MutableStateFlow<Task<List<EnglishItVerbModel>>>(Task.Initial)
    private val englishItVerbsFlowSource =
        MutableStateFlow<List<EnglishItVerbModel>>(emptyList())
    private val screenContentFlowSource = MutableStateFlow(EnglishItVerbsScreenContent(emptyList()))

    // Base param(s)
    override val screenContentFlow: Flow<EnglishItVerbsScreenContent>
        get() = screenContentFlowSource

    init {
        setupTopAppBar(titleResId = R.string.label_englishItVerbs)

        executeForSuccessTaskResultUseCase(getEnglishItVerbsTaskFlowOrLoadFromFBUseCase) { data ->
            englishItVerbsFlowSource.value = data
        }
    }
}