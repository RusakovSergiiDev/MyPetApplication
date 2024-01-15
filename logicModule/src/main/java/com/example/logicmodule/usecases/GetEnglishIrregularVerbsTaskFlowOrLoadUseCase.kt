package com.example.logicmodule.usecases

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishIrregularVerbsTaskFlowOrLoadUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        return firebaseRepository.getEnglishIrregularVerbsTaskFlowOrLoad()
    }
}