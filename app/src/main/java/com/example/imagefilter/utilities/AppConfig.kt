package com.example.imagefilter.utilities

import android.app.Application
import com.example.imagefilter.dependencyinjection.repositoryModule
import com.example.imagefilter.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppConfig:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}