package com.example.logicmodule

import android.util.Log
import com.example.datamodule.dto.server.FeatureDto
import com.example.datamodule.mapper.mapToEnglishRulesModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.datamodule.types.getLogName
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
            delay(3000)
            throw NullPointerException("ABC")
            val englishRules = englishService.getEnglishRules()
            val englishRulesMapped = englishRules.mapToEnglishRulesModel()
            englishRulesTaskFlowSource.value = (Task.Success(englishRulesMapped))
        } catch (e: Exception) {
            englishRulesTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun getTestDataTaskFlowOrLoad(withError: Boolean = false): Flow<Task<Unit>> {
        Log.d(LOG_TAG, "getTestDataTaskFlowOrLoad(withError=$withError)")
        val currentTestDataTask = testDataTaskFlowSource.value
        Log.d(LOG_TAG, "currentTestDataTask: ${currentTestDataTask.getLogName()}")
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
        Log.d(LOG_TAG, "=====")
        Log.d(LOG_TAG, "loadTestData()")
        testDataTaskFlowSource.value = Task.Loading
        Log.d(LOG_TAG, "Task.Loading")
        try {
            delay(5000)
            testDataTaskFlowSource.value = (Task.Success(Unit))
            Log.d(LOG_TAG, "Task.Success")
        } catch (e: Exception) {
            Log.d(LOG_TAG, "catch:${e.message}")
            Log.d(LOG_TAG, "Task.Error:${e.message}")
            testDataTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun loadTestDataWithError() {
        Log.d(LOG_TAG, "=====")
        Log.d(LOG_TAG, "loadTestDataWithError()")
        testDataTaskFlowSource.value = Task.Loading
        Log.d(LOG_TAG, "Task.Loading")
        try {
            delay(5000)
            Log.d(LOG_TAG, "throw error")
            throw NullPointerException("Hardcoded NPE")
        } catch (e: Exception) {
            Log.d(LOG_TAG, "catch:${e.message}")
            Log.d(LOG_TAG, "Task.Error:${e.message}")
            testDataTaskFlowSource.value = (Task.Error(e.message.orEmpty()))
        }
    }

    suspend fun loadDataWithDelay(delay: Long): String {
        Log.d("myLogs", "loadDataWithDelay:$delay start")
        delay(delay)
        Log.d("myLogs", "loadDataWithDelay:$delay end")
        return "Result of $delay"
    }
}