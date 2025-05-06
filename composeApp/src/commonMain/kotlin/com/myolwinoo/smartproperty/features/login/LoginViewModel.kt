package com.myolwinoo.smartproperty.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.network.SPApi
import com.myolwinoo.smartproperty.utils.InputValidator
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

    var email by mutableStateOf(TextFieldValue())
        private set
    var password by mutableStateOf(TextFieldValue())
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

    var showLoginError by mutableStateOf(false)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events

    fun onEmailChange(value: TextFieldValue) {
        emailError = null
        email = value
    }

    fun onPasswordChange(value: TextFieldValue) {
        password = value
    }

    fun login() {
        viewModelScope.launch {
            if (!InputValidator.isValidEmail(email.text)) {
                emailError = "Invalid email"
                return@launch
            }

            isLoading = true
            spApi.login(
                email = email.text,
                password = password.text
            ).onSuccess {
                _events.emit(LoginEvent.LoginSuccess)
            }.onFailure {
                showLoginError = true
            }
            isLoading = false
        }
    }

    fun dismissLoginError() {
        showLoginError = false
    }
}