package com.example.datamodule.dto.server

data class EnglishRulesDto(
    val timeRules: List<EnglishTimeRuleDto> = emptyList(),
    val conditionals: List<EnglishConditionalRuleDto> = emptyList()
)
