package com.example.logicmodule.usecases.firebase

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishIrregularVerbsTaskFlowOrLoadFromFBUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): IFlowTaskUseCase<List<EnglishIrregularVerbModel>> {

    override suspend fun execute(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        return firebaseRepository.getEnglishIrregularVerbsTaskFlowOrLoad()
    }

    override suspend fun retry() {
        // firebaseRepository.get
    }
}