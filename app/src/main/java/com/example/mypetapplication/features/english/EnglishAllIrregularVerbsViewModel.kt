package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.logicmodule.usecases.firebase.GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishAllIrregularScreenContent
import com.example.mypetapplication.features.english.mappers.EnglishUiMapper
import com.example.mypetapplication.utils.log
import com.example.presentationmodule.R
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EnglishAllIrregularVerbsViewModel @Inject constructor(
    getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase,
    uiMapper: EnglishUiMapper,
) : BaseContentViewModel<EnglishAllIrregularScreenContent>() {

    // Internal param(s)
    private val isShowTranslateFlowSource = MutableStateFlow(false)
    private val englishAllIrregularVerbsFlowSource =
        MutableStateFlow<List<EnglishIrregularVerbModel>>(emptyList())
    private val englishAllIrregularVerbsMappedFlow =
        englishAllIrregularVerbsFlowSource.map { models ->
            log("map UI")
            models.forEachIndexed { index, englishIrregularVerbModel ->
                englishIrregularVerbModel.index = index
            }
            uiMapper.mapToUiItems(models)
        }
    private val contentLiveData: LiveData<EnglishAllIrregularScreenContent> =
        combine(
            isShowTranslateFlowSource,
            englishAllIrregularVerbsMappedFlow
        ) { isShowTranslate, englishAllIrregularVerbs ->
            log("combine")
            EnglishAllIrregularScreenContent(
                isShowTranslate, englishAllIrregularVerbs
            )
        }.asLiveData()

    // Base fun(s)
    override fun getTopAppBarTitleResId() = R.string.label_allEnglishIrregularVerbs

    init {
        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase) {
            englishAllIrregularVerbsFlowSource.value = it
        }
        setTopAppBarAction(
            TopAppBarAction.ToggleAction(
                onCheckedChange = { isShowTranslateFlowSource.value = it },
                isChecked = isShowTranslateFlowSource.asLiveData()
            )
        )
        registerContentSource(contentLiveData)
    }
}