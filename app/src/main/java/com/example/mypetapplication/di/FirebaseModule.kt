package com.example.mypetapplication.di

import com.example.logicmodule.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepository()
    }
}