package com.example.datamodule.dagger

import android.content.Context
import com.example.datamodule.map.DtoMapper
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDtoMapper(context: Context) =
        DtoMapper(context)
}