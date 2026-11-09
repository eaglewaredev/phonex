package dev.eagelware.phonex

import android.app.Application
import android.content.Context
import dev.eagelware.phonex.di.initKoin

class MainApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }
}