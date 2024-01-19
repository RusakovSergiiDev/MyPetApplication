package com.example.logicmodule.network

import com.example.datamodule.dto.server.EnglishRulesDto
import retrofit2.http.GET

interface EnglishService {

    @GET("english/rules")
    suspend fun getEnglishRules(): EnglishRulesDto
}