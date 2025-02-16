package com.myolwinoo.smartproperty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual class DataStoreProvider {

    actual fun get(): DataStore<Preferences> {
        return createDataStore {
            val userDir = File(System.getProperty("user.home"))
            val appDir = File(userDir, "SmartProperty")
            appDir.mkdirs()
            appDir.resolve(dataStoreFileName).absoluteFile.path.also {
                println("Data store path: $it")
            }
        }
    }
}