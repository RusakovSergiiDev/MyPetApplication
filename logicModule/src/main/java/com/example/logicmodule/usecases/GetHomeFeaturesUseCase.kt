package com.example.logicmodule.usecases

import com.example.datamodule.models.HomeMainOptionModel
import com.example.datamodule.types.HomeMainOptionType
import com.example.datamodule.types.Task
import com.example.presentationmodule.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHomeFeaturesUseCase @Inject constructor() : IFlowTaskUseCase<List<HomeMainOptionModel>> {

    override suspend fun execute(): Flow<Task<List<HomeMainOptionModel>>> {
        return flow {
            val result = mutableListOf<HomeMainOptionModel>()
            val englishRulesItem = HomeMainOptionModel(
                type = HomeMainOptionType.ENGLISH_RULES,
                titleResId = R.string.label_englishRules,
            )
            result.add(englishRulesItem)
            val englishItVerbsItem = HomeMainOptionModel(
                type = HomeMainOptionType.ENGLISH_IT_VERBS,
                titleResId = R.string.label_itVerbs,
            )
            result.add(englishItVerbsItem)
            val englishIrregularVerbsItem = HomeMainOptionModel(
                type = HomeMainOptionType.ENGLISH_IRREGULAR_VERBS,
                titleResId = R.string.label_irregularVerbs,
                descriptionResId = R.string.label_irregularVerbsDescription,
            )
            result.add(englishIrregularVerbsItem)
            val spainVerbsItem = HomeMainOptionModel(
                type = HomeMainOptionType.SPANISH_TOP_200_VERBS,
                titleResId = R.string.label_homeOptionSpanishVerbs,
            )
            result.add(spainVerbsItem)
            val asyncTestItem = HomeMainOptionModel(
                type = HomeMainOptionType.ASYNC_TEST,
                titleResId = R.string.label_asyncTest,
            )
            result.add(asyncTestItem)
            emit(Task.Success(result.toList()))
        }
    }

    override suspend fun retry() {
        // Nothing
    }

}