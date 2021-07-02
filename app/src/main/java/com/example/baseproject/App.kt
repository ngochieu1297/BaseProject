package com.example.baseproject

import android.app.Application
import com.example.baseproject.di.dataModule
import com.example.baseproject.di.networkModule
import com.example.baseproject.di.viewModelModule
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(networkModule, dataModule, viewModelModule) }
        Timber.plant(Timber.DebugTree())
    }
}
