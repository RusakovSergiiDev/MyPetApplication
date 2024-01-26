package com.example.logicmodule.repository

import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.mapper.mapToEnglishRulesModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.datamodule.types.isInitial
import com.example.logicmodule.network.EnglishService
import com.example.logicmodule.network.FeatureService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val featureService: FeatureService,
    private val englishService: EnglishService
) {

    companion object {
        private const val LOG_TAG = "CONTENT_REPOSITORY"
    }

    // Internal param(s)
    private val testDataTaskFlowSource = MutableStateFlow<Task<Unit>>(Task.Initial)
    private val featureListTaskFlowSource = MutableStateFlow<Task<List<FeatureDto>>>(Task.Initial)
    private val featureListFlowSource = MutableStateFlow<List<FeatureDto>>(emptyList())
    private val englishRulesTaskFlowSource = MutableStateFlow<Task<EnglishRulesModel>>(Task.Initial)

    // External param(s)
    val testDataTaskFlow: Flow<Task<Unit>> = testDataTaskFlowSource
    val featureListTaskFlow: Flow<Task<List<FeatureDto>>> = featureListTaskFlowSource
    val featureListFlow: Flow<List<FeatureDto>> = featureListFlowSource
    val englishRulesTaskFlow: Flow<Task<EnglishRulesModel>> = englishRulesTaskFlowSource

    suspend fun getFeatureListTaskFlowOrLoad(): Flow<Task<List<FeatureDto>>> {
        if (featureListTaskFlowSource.value.isInitial()) {
            loadFeatureList()
        }
        return featureListTaskFlow
    }

    suspend fun loadFeatureList() {
        featureListTaskFlowSource.value = Task.Loading
        delay(3000)
        try {
            val featureList = featureService.getFeatures()
            featureListTaskFlowSource.value = Task.Success(featureList)
        } catch (e: Exception) {
            featureListTaskFlowSource.value = Task.Error(e.message.orEmpty())
        }
    }

    suspend fun getEnglishRulesTaskFlowOrLoad(): Flow<Task<EnglishRulesModel>> {
        if (englishRulesTaskFlowSource.value.isInitial()) {
            coroutineScope {
                launch { loadEnglishRules() }
            }
        }
        return englishRulesTaskFlow
    }

    suspend fun loadEnglishRules() {
        englishRulesTaskFlowSource.value = Task.Loading
        try {
            val englishRules = englishService.getEnglishRules()
            val englishRulesMapped = englishRules.mapToEnglishRulesModel()
            englishRulesTaskFlowSource.value = (Task.Success(englishRulesMapped))
        } catch (e: Exception) {
            englishRulesTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun getTestDataTaskFlowOrLoad(withError: Boolean = false): Flow<Task<Unit>> {
        if (testDataTaskFlowSource.value.isInitial()) {
            coroutineScope {
                if (withError) {
                    launch { loadTestDataWithError() }
                } else {
                    launch { loadTestData() }
                }
            }
        }
        return testDataTaskFlow
    }

    suspend fun loadTestData() {
        testDataTaskFlowSource.value = Task.Loading
        try {
            delay(5000)
            testDataTaskFlowSource.value = (Task.Success(Unit))
        } catch (e: Exception) {
            testDataTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun loadTestDataWithError() {
        testDataTaskFlowSource.value = Task.Loading
        try {
            delay(5000)
            throw NullPointerException("Hardcoded NPE")
        } catch (e: Exception) {
            testDataTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }
}