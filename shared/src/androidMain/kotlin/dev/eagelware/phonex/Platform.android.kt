package dev.eagelware.phonex

import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.eagelware.phonex.di.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformDataStoreModule(): Module = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
}
