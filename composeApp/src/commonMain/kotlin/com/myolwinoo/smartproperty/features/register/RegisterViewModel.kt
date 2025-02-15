package com.myolwinoo.smartproperty.features.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val spApi: SPApi
): ViewModel() {

    var email by mutableStateOf(TextFieldValue(""))
        private set
    var password by mutableStateOf(TextFieldValue(""))
        private set
    var confirmPassword by mutableStateOf(TextFieldValue(""))
        private set
    var isLoading by mutableStateOf(false)
        private set
    val isRegisterEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { email.text },
        snapshotFlow { password.text },
        snapshotFlow { confirmPassword.text },
    ) { email, password, confirmPassword ->
        email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

    var showRegisterError by mutableStateOf(false)
        private set

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    fun onEmailChange(value: TextFieldValue) {
        email = value
    }

    fun onPasswordChange(value: TextFieldValue) {
        password = value
    }

    fun onConfirmPasswordChange(value: TextFieldValue) {
        confirmPassword = value
    }

    fun register() {
        viewModelScope.launch {
            isLoading = true
            spApi.register(
                email = email.text,
                password = password.text
            ).onSuccess {
                _events.emit("register_success")
            }.onFailure {
                showRegisterError = true
            }
            isLoading = false
        }
    }

    fun dismissRegisterError() {
        showRegisterError = false
    }
}