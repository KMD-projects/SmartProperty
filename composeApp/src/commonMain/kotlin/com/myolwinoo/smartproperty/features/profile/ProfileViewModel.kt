package com.myolwinoo.smartproperty.features.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    accountManager: AccountManager,
    private val spApi: SPApi,
): ViewModel() {

    val profile: StateFlow<User?> = accountManager.userFlow
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
}