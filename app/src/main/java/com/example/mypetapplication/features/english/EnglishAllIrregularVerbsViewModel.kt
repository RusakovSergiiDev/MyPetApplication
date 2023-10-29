package com.example.mypetapplication.features.english

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.repo.FirebaseRepo
import kotlinx.coroutines.flow.map

class EnglishAllIrregularVerbsViewModel : BaseViewModel() {

    // Internal param(s)
    private val firebaseRepo = FirebaseRepo
    private val englishIrregularVerbsSourceFlow = firebaseRepo.englishIrregularVerbsFlow
        .map { models ->
            models.forEachIndexed { index, englishIrregularVerbModel ->
                englishIrregularVerbModel.index = index
            }
            models
        }

    // External param(s)
    val englishIrregularVerbUiItemsLiveData: LiveData<List<EnglishIrregularVerbModel>> =
        englishIrregularVerbsSourceFlow.asLiveData()
}