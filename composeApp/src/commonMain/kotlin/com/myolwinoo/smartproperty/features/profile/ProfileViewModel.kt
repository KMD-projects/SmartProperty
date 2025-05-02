package com.myolwinoo.smartproperty.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.network.SPApi
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    accountManager: AccountManager,
    private val spApi: SPApi,
) : ViewModel() {

    var name by mutableStateOf(TextFieldValue())
    var email by mutableStateOf(TextFieldValue())
    var phone by mutableStateOf(TextFieldValue())
    var address by mutableStateOf(TextFieldValue())

    val profile: StateFlow<User?> = accountManager.userFlow
        .onEach {
            name = TextFieldValue(it?.name ?: "")
            email = TextFieldValue(it?.email ?: "")
            phone = TextFieldValue(it?.phone ?: "")
            address = TextFieldValue(it?.address ?: "")
        }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    var isLoading by mutableStateOf(false)
        private set

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            spApi.loadProfile()
            isLoading = false
        }
    }

    fun becomeLandlord() {
        viewModelScope.launch {
            spApi.becomeLandlord()
        }
    }

    fun logout() {
        viewModelScope.launch {
            spApi.logout()
            _events.emit("logout")
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            spApi.updateProfile(
                name = name.text,
                email = email.text,
                phone = phone.text,
                address = address.text
            ).onSuccess {
                refresh()
            }.onFailure {
                Napier.i { "Error: ${it.message}" }
            }
        }
    }

    fun cancelEdit() {
        name = TextFieldValue(profile.value?.name ?: "")
        email = TextFieldValue(profile.value?.email ?: "")
        phone = TextFieldValue(profile.value?.phone ?: "")
        address = TextFieldValue(profile.value?.address ?: "")
    }
}