package com.example.logicmodule.usecases.english

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.english.EnglishIrregularCampaignModel
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import com.example.logicmodule.usecases.IFlowTaskUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetEnglishIrregularVerbsCampaignTaskFlowUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): IFlowTaskUseCase<EnglishIrregularCampaignModel> {

    override suspend fun execute(): Flow<Task<EnglishIrregularCampaignModel>> {
        return callbackFlow {
            trySend(Task.Loading)
            firebaseRepository.getEnglishIrregularVerbsTaskFlowOrLoad().collect { task ->
                if (task is Task.Success) {
                    val models = task.data
                    val campaign = generateCampaign(models)
                    trySend(Task.Success(campaign))
                    close()
                } else {

                }
            }
        }

    }

    private fun generateCampaign(models: List<EnglishIrregularVerbModel>): EnglishIrregularCampaignModel {
        val campaignModels = models.shuffled().take(10)
        val campaignMissions = campaignModels.map { EnglishIrregularMissionModel(it) }
        return EnglishIrregularCampaignModel(campaignMissions)
    }

    override suspend fun retry() {

    }
}