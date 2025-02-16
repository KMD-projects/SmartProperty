package com.myolwinoo.smartproperty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.myolwinoo.smartproperty.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AccountManager(
    private val dataStore: DataStore<Preferences>
) {
    private val keyLoggedInUser = stringPreferencesKey("logged_in_user")

    val userFlow = dataStore.data
        .map {
            it[keyLoggedInUser]
                ?.let { Json.decodeFromString<User>(it) }
        }

    val isLoggedInFlow = userFlow.map { it != null }

    suspend fun isLoggedIn(): Boolean = getUser() != null

    suspend fun getUser(): User? = withContext(Dispatchers.Default) {
        runCatching {
            userFlow.first()
        }.fold({ it }, { null })
    }

    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[keyLoggedInUser] = Json.encodeToString(user)
        }
    }

    suspend fun removeUser() {
        dataStore.edit {
            it.remove(keyLoggedInUser)
        }
    }
}