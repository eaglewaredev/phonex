package dev.eagelware.phonex.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(): DataStore<Preferences> = createDataStore(
    storage = FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { context.filesDir.resolve(dataStoreFileName) }
    )
)