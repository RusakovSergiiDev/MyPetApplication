package com.example.datamodule.dto.server

data class EnglishRulesDto(
    val timeRules: List<EnglishTimeRuleDto>,
    val conditionals: List<EnglishConditionalRuleDto>
)
