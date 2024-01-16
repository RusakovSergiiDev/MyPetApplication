package com.example.datamodule.mapper

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.dto.server.EnglishConditionalRuleDto
import com.example.datamodule.dto.server.EnglishRulesDto
import com.example.datamodule.dto.server.EnglishTimeRuleDto
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.models.english.EnglishConditionalRuleModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.models.english.EnglishTimeRuleModel

fun EnglishIrregularVerbDto.mapToEnglishIrregularVerbModel(): EnglishIrregularVerbModel? {
    val infinitive = infinitive ?: return null
    val simplePast = pastSimple ?: return null
    val pastParticiple = pastParticiple ?: return null
    val translateInUkrainian = translateInUkrainian ?: return null
    val isPopular = isPopular ?: false
    return EnglishIrregularVerbModel(
        infinitive = infinitive,
        pastSimple = simplePast,
        pastParticiple = pastParticiple,
        translateInUkrainian = translateInUkrainian,
        isPopular = isPopular
    )
}

fun List<EnglishIrregularVerbDto>.mapToEnglishIrregularVerbModelList(): List<EnglishIrregularVerbModel> {
    return this.mapNotNull { item ->
        item.mapToEnglishIrregularVerbModel()
    }
}

fun SpanishVerbDto.mapSpanishVerbModel(): SpanishVerbModel? {
    val english = en ?: return null
    val spanish = es ?: return null
    val ukrainian = ua ?: return null
    return SpanishVerbModel(
        english = english,
        spanish = spanish,
        ukrainian = ukrainian
    )
}

fun List<SpanishVerbDto>.mapSpanishVerbModelList(): List<SpanishVerbModel> {
    return this.mapNotNull { item ->
        item.mapSpanishVerbModel()
    }
}

fun EnglishTimeRuleDto.mapToEnglishTimeRuleModel(): EnglishTimeRuleModel {
    return EnglishTimeRuleModel(
        ruleName = this.ruleName,
        structure = this.structure,
        example = this.example
    )
}

fun EnglishConditionalRuleDto.mapToEnglishConditionalRuleModel(): EnglishConditionalRuleModel {
    return EnglishConditionalRuleModel(
        conditionalName = this.conditionalName,
        structure = this.structure,
        example = this.example
    )
}

fun EnglishRulesDto.mapToEnglishRulesModel(): EnglishRulesModel {
    return EnglishRulesModel(
        timeRules = this.timeRules.map { it.mapToEnglishTimeRuleModel() },
        conditionals = this.conditionals.map { it.mapToEnglishConditionalRuleModel() }
    )
}