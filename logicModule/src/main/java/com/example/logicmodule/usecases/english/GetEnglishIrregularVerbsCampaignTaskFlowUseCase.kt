package com.example.logicmodule.usecases.english

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetEnglishIrregularVerbsCampaignTaskFlowUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : IFlowTaskUseCase<List<EnglishIrregularMissionModel>> {

    override suspend fun execute(): Flow<Task<List<EnglishIrregularMissionModel>>> {
        return callbackFlow {
            trySend(Task.Loading)
            firebaseRepository.getEnglishIrregularVerbsTaskFlowOrLoad().collect { task ->
                if (task is Task.Success) {
                    val models = task.data
                    val missions = generateCampaign(models)
                    trySend(Task.Success(missions))
                    close()
                } else {

                }
            }
        }

    }

    private fun generateCampaign(models: List<EnglishIrregularVerbModel>): List<EnglishIrregularMissionModel> {
        val campaignModels = models.shuffled().take(10)
        val campaignMissions = campaignModels.map { EnglishIrregularMissionModel(it) }
        return campaignMissions
    }

    override suspend fun retry() {

    }
}