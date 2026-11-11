package dev.eagelware.phonex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer

//actual fun createDataStore(context: Any?): DataStore<Preferences> {
//    val appContext = (context as? Context) ?: throw IllegalArgumentException("Android requires a valid Context")
//    return createDataStoreWithPath {
//        appContext.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
//    }
//}


actual fun createDataStore(): DataStore<Preferences> = DataStoreFactory.create(
    storage = FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { context .filesDir.resolve(DATASTORE_FILE_NAME) }
    )
)

private lateinit var applicationContext: Context
fun initPreferencesDataStore(appContext: Context) {
    applicationContext = appContext
}


fun getPreferencesDataStorePath(appContext: Context): String =
    appContext.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath

//actual fun createPreferencesDataStore(): DataStore<Preferences> {
//    val path = getPreferencesDataStorePath(applicationContext)
//    return getPreferencesDataStore(path)
//}