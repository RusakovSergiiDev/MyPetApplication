package com.example.logicmodule.network

import com.example.datamodule.dto.server.FeatureDto
import retrofit2.http.GET

interface FeatureService {

    @GET("features")
    suspend fun getFeatures(): List<FeatureDto>
}