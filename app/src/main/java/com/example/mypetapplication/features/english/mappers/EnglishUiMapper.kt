package com.example.mypetapplication.features.english.mappers

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.english.EnglishConditionalModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.models.english.EnglishTimeRuleModel
import com.example.presentationmodule.data.EnglishConditionalUiItem
import com.example.presentationmodule.data.EnglishIrregularVerbUiItem
import com.example.presentationmodule.data.EnglishRulesUiModel
import com.example.presentationmodule.data.EnglishTimeRuleUiItem
import javax.inject.Inject

class EnglishUiMapper @Inject constructor() {

    private fun EnglishIrregularVerbModel.mapToUiItem(): EnglishIrregularVerbUiItem =
        EnglishIrregularVerbUiItem(
            index = index,
            infinitive = this.infinitive,
            pastSimple = this.pastSimple,
            pastParticiple = this.pastParticiple,
            translateInUkrainian = this.translateInUkrainian
        )

    fun EnglishTimeRuleModel.mapEnglishTimeRuleModelToUiItem() =
        EnglishTimeRuleUiItem(
            ruleName = this.ruleName,
            structure = this.structure,
            example = this.example
        )

    fun EnglishConditionalModel.mapEnglishConditionalModelToUiItem() =
        EnglishConditionalUiItem(
            conditionalName = this.conditionalName,
            structure = this.structure,
            example = this.example
        )

    fun List<EnglishTimeRuleModel>.mapEnglishTimeRuleModelsToUiItems() =
        this.map { item -> item.mapEnglishTimeRuleModelToUiItem() }

    fun List<EnglishConditionalModel>.mapEnglishConditionalModelsToUiItems() =
        this.map { item -> item.mapEnglishConditionalModelToUiItem() }

    fun mapToUiModel(item: EnglishRulesModel): EnglishRulesUiModel {
        return EnglishRulesUiModel(
            timeRules = item.timeRules.mapEnglishTimeRuleModelsToUiItems(),
            conditionals = item.conditionals.mapEnglishConditionalModelsToUiItems()
        )
    }

    fun mapToUiItems(items: List<EnglishIrregularVerbModel>): List<EnglishIrregularVerbUiItem> {
        return items.map { item -> item.mapToUiItem() }
    }
}