package com.example.logicmodule.usecases.firebase

import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.logicmodule.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishRulesTaskFlowOrLoadFromFBUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): IFlowTaskUseCase<EnglishRulesModel> {

    override suspend fun execute(): Flow<Task<EnglishRulesModel>> {
        return firebaseRepository.getEnglishRulesTaskFlowOrLoad()
    }

    override suspend fun retry() {
        firebaseRepository.loadEnglishRules()
    }
}