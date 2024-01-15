package com.example.mypetapplication.di

import com.example.logicmodule.AnalyticsRepository
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