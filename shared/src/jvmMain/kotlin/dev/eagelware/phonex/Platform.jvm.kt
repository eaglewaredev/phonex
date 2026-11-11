package dev.eagelware.phonex

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.eagelware.phonex.di.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun platformDataStoreModule(): Module = module {
    single<DataStore<Preferences>> {
        print("platform jvm data store called")
        createDataStore()
    }
}
