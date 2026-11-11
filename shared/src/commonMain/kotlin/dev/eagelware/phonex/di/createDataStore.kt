package dev.eagelware.phonex.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory


val DATASTORE_FILE_NAME = "settings.preferences_pb"
//fun getPreferencesDataStore(path: String) = PreferenceDataStoreFactory.createWithPath {
//    path.toPath()
//}

//expect fun createPreferencesDataStore(): DataStore<Preferences>
expect fun createDataStore(): DataStore<Preferences>

// Helper to construct the actual DataStore instance once given a absolute path string
//fun createDataStoreWithPath(producePath: () -> String): DataStore<Preferences> {
//    return PreferenceDataStoreFactory.createWithPath(
//        producePath = { producePath().toPath() }
//    )
//}
//fun createDataStore(storage: DataStore<Preferences>): DataStore<Preferences> =
//    DataStoreFactory.create(storage = storage)

