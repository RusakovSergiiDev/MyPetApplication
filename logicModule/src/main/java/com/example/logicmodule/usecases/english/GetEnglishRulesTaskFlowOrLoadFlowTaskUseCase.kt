package com.example.logicmodule.usecases.english

import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.ContentRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishRulesTaskFlowOrLoadFlowTaskUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) : IFlowTaskUseCase<EnglishRulesModel> {

    override suspend fun execute(): Flow<Task<EnglishRulesModel>> {
        return contentRepository.getEnglishRulesTaskFlowOrLoad()
    }

    override suspend fun retry() {
        contentRepository.loadEnglishRules()
    }
}