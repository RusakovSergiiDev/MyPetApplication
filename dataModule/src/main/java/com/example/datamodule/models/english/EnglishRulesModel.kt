package com.example.datamodule.models.english

data class EnglishRulesModel(
    val timeRules: List<EnglishTimeRuleModel>,
    val conditionals: List<EnglishConditionalRuleModel>
)
