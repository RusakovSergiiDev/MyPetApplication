package com.example.logicmodule.usecases

import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeSpanishTop200VerbsTaskFlowOrLoadUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(): Flow<Task<List<SpanishVerbModel>>> {
        return firebaseRepository.getSpanishTop200VerbsTaskFlowOrLoad()
    }
}