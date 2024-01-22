package com.example.mypetapplication.di

import com.example.logicmodule.repository.AnalyticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    fun provideAnalyticsRepository(): AnalyticsRepository {
        return AnalyticsRepository()
    }
}