package com.example.mypetapplication.features.english

import androidx.lifecycle.MutableLiveData
import com.example.logicmodule.usecases.firebase.GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase
import com.example.presentationmodule.R
import com.example.mypetapplication.base.BaseContentViewModel
import com.example.mypetapplication.features.english.data.EnglishIrregularScreenContent
import com.example.mypetapplication.utils.SimpleNavigationEvent
import com.example.presentationmodule.data.TopAppBarAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnglishIrregularVerbsViewModel @Inject constructor(
    getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase: GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase,
) : BaseContentViewModel<EnglishIrregularScreenContent>() {

    // Internal param(s)
    private val contentLiveDataSource = MutableLiveData<EnglishIrregularScreenContent>()

    // Base fun(s)
    override fun getTopAppBarTitleResId() = R.string.label_englishIrregularVerbs

    // Event(s)
    val navigateToSeeAllEvent = SimpleNavigationEvent()

    init {
        executeForSuccessTaskResultUseCase(getEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase) {

        }
        setTopAppBarAction(
            TopAppBarAction.TextAction(
                textResId = R.string.label_seeAll,
                callback = ::onSeeAllClicked
            )
        )
        registerContentSource(contentLiveDataSource)
    }

    private fun onSeeAllClicked() {
        navigateToSeeAllEvent.call()
    }
}