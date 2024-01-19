package com.example.logicmodule

import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.mapper.mapToEnglishRulesModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.datamodule.types.isInitial
import com.example.logicmodule.network.EnglishService
import com.example.logicmodule.network.FeatureService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val featureService: FeatureService,
    private val englishService: EnglishService
) {

    // Internal param(s)
    private val testDataTaskSourceFlow = MutableStateFlow<Task<Unit>>(Task.Initial)
    private val featureListTaskSourceFlow = MutableStateFlow<Task<List<FeatureDto>>>(Task.Initial)
    private val featureListSourceFlow = MutableStateFlow<List<FeatureDto>>(emptyList())
    private val englishRulesTaskSourceFlow = MutableStateFlow<Task<EnglishRulesModel>>(Task.Initial)

    // External param(s)
    val testDataTaskFlow: Flow<Task<Unit>> = testDataTaskSourceFlow
    val featureListTaskFlow: Flow<Task<List<FeatureDto>>> = featureListTaskSourceFlow
    val featureListFlow: Flow<List<FeatureDto>> = featureListSourceFlow
    val englishRulesTaskFlow: Flow<Task<EnglishRulesModel>> = englishRulesTaskSourceFlow

    suspend fun getFeatureListTaskFlowOrLoad(): Flow<Task<List<FeatureDto>>> {
        if (featureListTaskSourceFlow.value.isInitial()) {
            loadFeatureList()
        }
        return featureListTaskFlow
    }

    suspend fun loadFeatureList() {
        featureListTaskSourceFlow.value = Task.Loading
        delay(3000)
        try {
            val featureList = featureService.getFeatures()
            featureListTaskSourceFlow.value = Task.Success(featureList)
        } catch (e: Exception) {
            featureListTaskSourceFlow.value = Task.Error(e.message.orEmpty())
        }
    }

    suspend fun getEnglishRulesTaskFlowOrLoad(): Flow<Task<EnglishRulesModel>> {
        if (englishRulesTaskSourceFlow.value.isInitial()) {
            loadEnglishRules()
        }
        return englishRulesTaskFlow
    }

    suspend fun loadEnglishRules() {
        englishRulesTaskSourceFlow.value = Task.Loading
        try {
            val englishRules = englishService.getEnglishRules()
            val englishRulesMapped = englishRules.mapToEnglishRulesModel()
            englishRulesTaskSourceFlow.value = (Task.Success(englishRulesMapped))
        } catch (e: Exception) {
            englishRulesTaskSourceFlow.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun getTestDataTaskFlowOrLoad(withError: Boolean = false): Flow<Task<Unit>> {
        if (testDataTaskSourceFlow.value.isInitial()) {
            if (withError) {
                loadTestDataWithError()
            } else {
                loadTestData()
            }
        }
        return testDataTaskFlow
    }

    suspend fun loadTestData() {
        testDataTaskSourceFlow.value = Task.Loading
        try {
            delay(500)
            testDataTaskSourceFlow.value = (Task.Success(Unit))
        } catch (e: Exception) {
            testDataTaskSourceFlow.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun loadTestDataWithError() {
        testDataTaskSourceFlow.value = Task.Loading
        try {
            delay(500)
            throw NullPointerException("Hardcoded NPE")
            delay(500)
            testDataTaskSourceFlow.value = (Task.Success(Unit))
        } catch (e: Exception) {
            testDataTaskSourceFlow.value = (Task.Error(e.message.orEmpty()))
        }
    }
}