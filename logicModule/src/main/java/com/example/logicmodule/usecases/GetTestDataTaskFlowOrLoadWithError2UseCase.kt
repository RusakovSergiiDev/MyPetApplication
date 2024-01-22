package com.example.logicmodule.usecases

import com.example.datamodule.types.Task
import com.example.logicmodule.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTestDataTaskFlowOrLoadWithError2UseCase @Inject constructor(
    private val contentRepository: ContentRepository
) : IFlowTaskUseCase<Unit> {

    override suspend fun execute(): Flow<Task<Unit>> {
        throw NullPointerException("Hardcoded NPE")
        return contentRepository.getTestDataTaskFlowOrLoad(withError = true)
    }

    override suspend fun retry() {
        contentRepository.loadTestDataWithError()
    }
}