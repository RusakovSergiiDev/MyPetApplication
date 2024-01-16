package com.example.logicmodule.usecases.english

import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.logicmodule.ContentRepository
import com.example.logicmodule.usecases.IUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishRulesTaskFlowOrLoadUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) : IUseCase<EnglishRulesModel> {

    override suspend fun execute(): Flow<Task<EnglishRulesModel>> {
        return contentRepository.getEnglishRulesTaskFlowOrLoad()
    }

    override suspend fun retry() {
        contentRepository.loadEnglishRules()
    }
}