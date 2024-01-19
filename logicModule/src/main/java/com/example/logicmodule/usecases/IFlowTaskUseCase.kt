package com.example.logicmodule.usecases

import com.example.datamodule.types.Task
import kotlinx.coroutines.flow.Flow

interface IFlowTaskUseCase<T> {
    suspend fun execute(): Flow<Task<T>>
    suspend fun retry()
}