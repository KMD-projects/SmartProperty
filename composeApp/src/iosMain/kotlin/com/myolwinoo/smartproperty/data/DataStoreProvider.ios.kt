@file:OptIn(ExperimentalForeignApi::class)

package com.myolwinoo.smartproperty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.myolwinoo.smartproperty.utils.getDocumentDirectory
import kotlinx.cinterop.ExperimentalForeignApi

actual class DataStoreProvider {

    actual fun get(): DataStore<Preferences> {
        return createDataStore {
            getDocumentDirectory().path + "/${dataStoreFileName}"
        }
    }
}