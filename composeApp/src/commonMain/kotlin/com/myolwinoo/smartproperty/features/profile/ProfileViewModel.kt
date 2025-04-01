package com.myolwinoo.smartproperty.features.profile

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

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    init {
        viewModelScope.launch {
            spApi.loadProfile()
        }
    }

    fun logout() {
        viewModelScope.launch {
            spApi.logout()
            _events.emit("logout")
        }
    }
}