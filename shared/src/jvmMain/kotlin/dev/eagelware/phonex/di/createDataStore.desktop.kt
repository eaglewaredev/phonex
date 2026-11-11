package dev.eagelware.phonex.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import java.io.File

actual fun createDataStore(): DataStore<Preferences> = DataStoreFactory.create(
    storage = FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { File(System.getProperty("java.io.tmpdir"), DATASTORE_FILE_NAME) }
    )
)

//actual fun createPreferencesDataStore(): DataStore<Preferences> {
//    return createDataStoreWithPath {
//        File(System.getProperty("user.home"), DATASTORE_FILE_NAME).absolutePath
//    }
//}

//fun createDataStore(): DataStore<Preferences> = createDataStore(
//    storage = FileStorage(
//        serializer = PreferencesFileSerializer,
//        produceFile = { File(System.getProperty("java.io.tmpdir"), dataStoreFileName) }
//    )