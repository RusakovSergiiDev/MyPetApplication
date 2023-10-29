package com.example.mypetapplication.features.spain

import androidx.lifecycle.asLiveData
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.repo.FirebaseRepo
import kotlinx.coroutines.flow.map

class SpanishTop200VerbsViewModel : BaseViewModel() {

    // Internal param(s)
    private val firebaseRepo = FirebaseRepo
    private val spanishTop200VerbsSourceFlow = firebaseRepo.spanishTop200VerbsFlow
        .map { models ->
            models.forEachIndexed { index, spanishTop200VerbsModel ->
                spanishTop200VerbsModel.index = index
            }
            models
        }

    // External param(s)
    val spanishTop200VerbsLiveData = spanishTop200VerbsSourceFlow.asLiveData()
}