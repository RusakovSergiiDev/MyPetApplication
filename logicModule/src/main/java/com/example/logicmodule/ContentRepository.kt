package com.example.logicmodule

import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.types.Task
import com.example.logicmodule.network.FeatureService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val featureService: FeatureService
) {

    // Internal param(s)
    private val featureListTaskSourceFlow = MutableStateFlow<Task<List<FeatureDto>>>(Task.Initial)
    private val featureListSourceFlow = MutableStateFlow<List<FeatureDto>>(emptyList())

    // External param(s)
    val featureListTaskFlow: Flow<Task<List<FeatureDto>>> = featureListTaskSourceFlow
    val featureListFlow: Flow<List<FeatureDto>> = featureListSourceFlow

    suspend fun getFeatureListTaskFlowOrLoad(): Flow<Task<List<FeatureDto>>> {
        val currentTask = featureListTaskSourceFlow.value
        if (currentTask is Task.Initial) {
            loadFeatureList()
        }
        return featureListTaskFlow
    }

    private suspend fun loadFeatureList() {
        featureListTaskSourceFlow.value = Task.Loading
        try {
            val featureList = featureService.getFeatures()
            featureListTaskSourceFlow.value = Task.Success(featureList)
        } catch (e: Exception) {
            featureListTaskSourceFlow.value = Task.Error(e.message.orEmpty())
        }
    }
}