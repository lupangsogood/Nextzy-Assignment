package com.candidate.android.dev.myapplication.Applcation

import android.app.Application
import com.candidate.android.dev.myapplication.BuildConfig
import com.candidate.android.dev.myapplication.di.appModule
import com.candidate.android.dev.myapplication.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(appModule,roomModule))
        }
        setUpTimber()
    }

    private fun setUpTimber(){
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}