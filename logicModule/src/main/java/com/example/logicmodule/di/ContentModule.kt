package com.example.logicmodule.di

import com.example.logicmodule.repository.ContentRepository
import com.example.logicmodule.network.EnglishService
import com.example.logicmodule.network.FeatureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContentModule {

    @Provides
    @Singleton
    fun provideContentRepository(
        featureService: FeatureService,
        englishService: EnglishService
    ): ContentRepository {
        return ContentRepository(featureService, englishService)
    }
}