package com.myolwinoo.smartproperty.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountManager: AccountManager,
): ViewModel() {

    val profile: StateFlow<User?> = accountManager.userFlow
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    fun logout() {
        viewModelScope.launch {
            accountManager.logout()
            _events.emit("logout")
        }
    }
}