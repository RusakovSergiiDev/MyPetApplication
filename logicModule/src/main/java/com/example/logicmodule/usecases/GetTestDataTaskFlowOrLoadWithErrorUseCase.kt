package com.example.logicmodule.usecases

import com.example.datamodule.types.Task
import com.example.logicmodule.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTestDataTaskFlowOrLoadWithErrorUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) : IFlowTaskUseCase<Unit> {

    override suspend fun execute(): Flow<Task<Unit>> {
        return contentRepository.getTestDataTaskFlowOrLoad(withError = true)
    }

    override suspend fun retry() {
        contentRepository.loadTestDataWithError()
    }
}