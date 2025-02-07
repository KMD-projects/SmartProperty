package com.myolwinoo.smartproperty.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val spApi: SPApi
): ViewModel() {

    var email by mutableStateOf(TextFieldValue(""))
        private set
    var password by mutableStateOf(TextFieldValue(""))
        private set
    var isLoading by mutableStateOf(false)
        private set
    val isLoginEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { email.text },
        snapshotFlow { password.text }
    ) { email, password ->
        email.isNotBlank() && password.isNotBlank()
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    fun onEmailChange(value: TextFieldValue) {
        email = value
    }

    fun onPasswordChange(value: TextFieldValue) {
        password = value
    }

    fun login() {
        viewModelScope.launch {
            isLoading = true
            delay(1000)
            isLoading = false
            _events.emit("login_success")
        }
    }
}