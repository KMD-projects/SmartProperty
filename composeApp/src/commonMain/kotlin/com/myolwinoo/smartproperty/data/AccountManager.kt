package com.myolwinoo.smartproperty.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.myolwinoo.smartproperty.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AccountManager(
    private val dataStore: DataStore<Preferences>
) {
    private val keyLoggedInUser = stringPreferencesKey("logged_in_user")
    private val keyToken = stringPreferencesKey("token")

    val userFlow = dataStore.data
        .map {
            it[keyLoggedInUser]
                ?.let {
                    runCatching { Json.decodeFromString<User>(it) }
                        .getOrNull()
                }
        }

    val isLoggedInFlow = userFlow.map { it != null }

    fun isLoggedIn(): Boolean = getToken() != null

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

    private suspend fun removeUser() {
        dataStore.edit {
            it.remove(keyLoggedInUser)
        }
    }

    fun getToken(): String? = runBlocking {
        dataStore.data.first()[keyToken]
            ?.ifBlank { null }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[keyToken] = token
        }
    }

    private suspend fun removeToken() {
        dataStore.edit {
            it.remove(keyToken)
        }
    }

    suspend fun logout() {
        removeUser()
        removeToken()
    }
}