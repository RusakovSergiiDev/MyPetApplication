package com.example.logicmodule.usecases

import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.types.Task
import com.example.logicmodule.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeatureListUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {

    suspend fun execute(): Flow<Task<List<FeatureDto>>> {
         return contentRepository.getFeatureListTaskFlowOrLoad()
    }
}