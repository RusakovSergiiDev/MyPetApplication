package com.example.logicmodule.usecases.firebase

import com.example.datamodule.models.english.EnglishItVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishItVerbsTaskFlowOrLoadFromFBUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): IFlowTaskUseCase<List<EnglishItVerbModel>> {

    override suspend fun execute(): Flow<Task<List<EnglishItVerbModel>>> {
        return firebaseRepository.getEnglishItVerbsTaskFlowOrLoad()
    }

    override suspend fun retry() {
        // firebaseRepository.get
    }
}