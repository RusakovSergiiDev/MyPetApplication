package com.example.mypetapplication

import android.app.Application
import com.example.mypetapplication.dagger.AppComponent
import com.example.mypetapplication.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}