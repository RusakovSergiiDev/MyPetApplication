package com.example.logicmodule.usecases.firebase

import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import com.example.logicmodule.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpanishTop200VerbsTaskFlowOrLoadUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : IUseCase<List<SpanishVerbModel>> {

    override suspend fun execute(): Flow<Task<List<SpanishVerbModel>>> {
        return firebaseRepository.getSpanishTop200VerbsTaskFlowOrLoad()
    }

    override suspend fun retry() {
        return firebaseRepository.loadSpanishVerbs()
    }
}