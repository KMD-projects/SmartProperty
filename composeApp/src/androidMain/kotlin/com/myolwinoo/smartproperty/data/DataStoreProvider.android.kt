package com.myolwinoo.smartproperty.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class DataStoreProvider(
    private val context: Context
) {

    actual fun get(): DataStore<Preferences> {
        return createDataStore {
            context.filesDir.resolve(dataStoreFileName).absolutePath
        }
    }
}