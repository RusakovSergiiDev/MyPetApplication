package com.example.logicmodule.usecases

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.logicmodule.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishIrregularVerbsFlowUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(): Flow<List<EnglishIrregularVerbModel>> {
        return firebaseRepository.englishIrregularVerbsFlow
    }
}