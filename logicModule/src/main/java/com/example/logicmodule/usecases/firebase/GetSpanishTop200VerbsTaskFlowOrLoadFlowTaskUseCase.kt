package com.example.logicmodule.usecases.firebase

import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpanishTop200VerbsTaskFlowOrLoadFlowTaskUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : IFlowTaskUseCase<List<SpanishVerbModel>> {

    override suspend fun execute(): Flow<Task<List<SpanishVerbModel>>> {
        return firebaseRepository.getSpanishTop200VerbsTaskFlowOrLoad()
    }

    override suspend fun retry() {
        return firebaseRepository.loadSpanishVerbs()
    }
}