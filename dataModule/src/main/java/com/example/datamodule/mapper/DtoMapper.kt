package com.example.datamodule.mapper

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.EnglishItVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.dto.server.EnglishConditionalRuleDto
import com.example.datamodule.dto.server.EnglishRulesDto
import com.example.datamodule.dto.server.EnglishTimeRuleDto
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.models.english.EnglishConditionalModel
import com.example.datamodule.models.english.EnglishItVerbModel
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

fun EnglishTimeRuleDto.mapToEnglishTimeRuleModel(): EnglishTimeRuleModel? {
    val ruleName = this.ruleName
    val structure = this.structure
    val example = this.example
    if (ruleName == null || structure == null || example == null) return null
    return EnglishTimeRuleModel(
        ruleName = ruleName,
        structure = structure,
        example = example
    )
}

fun EnglishConditionalRuleDto.mapToEnglishConditionalRuleModel(): EnglishConditionalModel? {
    val conditionalName = this.conditionalName
    val structure = this.structure
    val example = this.example
    if (conditionalName == null || structure == null || example == null) return null
    return EnglishConditionalModel(
        conditionalName = conditionalName,
        structure = structure,
        example = example
    )
}

fun EnglishRulesDto.mapToEnglishRulesModel(): EnglishRulesModel {
    return EnglishRulesModel(
        timeRules = this.timeRules.mapNotNull { it.mapToEnglishTimeRuleModel() },
        conditionals = this.conditionals.mapNotNull { it.mapToEnglishConditionalRuleModel() }
    )
}

fun EnglishItVerbDto.mapToEnglishItVerbModel(): EnglishItVerbModel? {
    return EnglishItVerbModel(
        english = this.english ?: return null,
        ukrainian = this.ukrainian ?: return null
    )
}

fun List<EnglishItVerbDto>.mapToEnglishItVerbModelList(): List<EnglishItVerbModel>{
    return this.mapNotNull { it.mapToEnglishItVerbModel() }
}