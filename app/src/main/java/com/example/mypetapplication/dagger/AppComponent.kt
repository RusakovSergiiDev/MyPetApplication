package com.example.mypetapplication.dagger

import android.content.Context
import com.example.datamodule.dagger.DataModule
import com.example.mypetapplication.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {

    fun injectTasksActivity(tasksActivity: MainActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}