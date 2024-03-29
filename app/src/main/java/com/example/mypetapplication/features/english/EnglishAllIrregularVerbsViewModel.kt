package com.example.mypetapplication.features.english

import androidx.lifecycle.asLiveData
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.logicmodule.usecases.firebase.GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishAllIrregularVerbsScreenContent
import com.example.mypetapplication.features.english.mappers.EnglishUiMapper
import com.example.presentationmodule.R
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EnglishAllIrregularVerbsViewModel @Inject constructor(
    getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase,
    uiMapper: EnglishUiMapper,
) : BaseContentViewModel<EnglishAllIrregularVerbsScreenContent>() {

    // Internal param(s)
    private val isShowTranslateFlowSource = MutableStateFlow(false)
    private val englishAllIrregularVerbsFlowSource =
        MutableStateFlow<List<EnglishIrregularVerbModel>>(emptyList())
    private val englishAllIrregularVerbsMappedFlow =
        englishAllIrregularVerbsFlowSource.map { models ->
            models.forEachIndexed { index, englishIrregularVerbModel ->
                englishIrregularVerbModel.index = index
            }
            uiMapper.mapToUiItems(models)
        }

    override val screenContentFlow: Flow<EnglishAllIrregularVerbsScreenContent> =
        combine(
            isShowTranslateFlowSource,
            englishAllIrregularVerbsMappedFlow
        ) { isShowTranslate, englishAllIrregularVerbs ->
            EnglishAllIrregularVerbsScreenContent(
                englishAllIrregularVerbs.onEach {
                    it.setIsShowTranslateInUkrainian(isShowTranslate)
                }
            )
        }

    init {
        setupTopAppBar(titleResId = R.string.label_allEnglishIrregularVerbs)
        setTopAppBarAction(
            TopAppBarAction.ToggleAction(
                onCheckedChange = { isShowTranslateFlowSource.value = it },
                isChecked = isShowTranslateFlowSource.asLiveData()
            )
        )

        registerScreenContentSource(screenContentFlow)

        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase) {
            englishAllIrregularVerbsFlowSource.value = it
        }
    }
}