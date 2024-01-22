package com.example.logicmodule.usecases

import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeatureListFlowTaskUseCase @Inject constructor(
    private val contentRepository: ContentRepository
): IFlowTaskUseCase<List<FeatureDto>> {

    override suspend fun execute(): Flow<Task<List<FeatureDto>>> {
         return contentRepository.getFeatureListTaskFlowOrLoad()
    }

    override suspend fun retry() {
        contentRepository.loadFeatureList()
    }
}