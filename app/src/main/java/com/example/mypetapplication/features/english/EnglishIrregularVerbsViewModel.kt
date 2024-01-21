package com.example.mypetapplication.features.english

import com.example.logicmodule.usecases.firebase.GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishIrregularScreenContent
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnglishIrregularVerbsViewModel @Inject constructor(
    getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase,
) : BaseContentViewModel<EnglishIrregularScreenContent>() {

    // Event(s)
    val navigateToSeeAllEvent = SimpleNavigationEvent()

    override val screenContentFlow: Flow<EnglishIrregularScreenContent> =
        MutableStateFlow(EnglishIrregularScreenContent())

    init {
        setupTopAppBar(titleResId = R.string.label_englishIrregularVerbs)
        setTopAppBarAction(
            TopAppBarAction.TextAction(
                textResId = R.string.label_seeAll,
                callback = ::onSeeAllClicked
            )
        )

        registerScreenContentSource(screenContentFlow)

        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase) {

        }
    }

    private fun onSeeAllClicked() {
        navigateToSeeAllEvent.call()
    }
}