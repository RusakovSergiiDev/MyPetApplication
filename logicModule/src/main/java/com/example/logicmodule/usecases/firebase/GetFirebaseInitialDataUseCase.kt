package com.example.logicmodule.usecases.firebase

import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFirebaseInitialDataUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun execute(): Flow<Boolean> {
        return combine(
            firebaseRepository.getEnglishIrregularVerbsTaskFlowOrLoad(),
            firebaseRepository.getSpanishTop200VerbsTaskFlowOrLoad()
        ) { englishIrregularVerbsTask, spanishTop200VerbsTask ->
            (englishIrregularVerbsTask is Task.Success || englishIrregularVerbsTask is Task.Empty)
                    && (spanishTop200VerbsTask is Task.Success || spanishTop200VerbsTask is Task.Error)
        }
    }
}