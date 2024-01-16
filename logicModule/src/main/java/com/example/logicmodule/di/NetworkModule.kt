package com.example.logicmodule.di

import com.example.logicmodule.network.EnglishService
import com.example.logicmodule.network.FeatureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mypetappdomain.free.beeceptor.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideFeatureService(retrofit: Retrofit): FeatureService {
        return retrofit.create(FeatureService::class.java)
    }

    @Provides
    @Singleton
    fun provideEnglishService(retrofit: Retrofit): EnglishService {
        return retrofit.create(EnglishService::class.java)
    }
}