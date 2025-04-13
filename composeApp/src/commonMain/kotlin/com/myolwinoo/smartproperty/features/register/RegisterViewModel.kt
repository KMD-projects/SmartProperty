package com.myolwinoo.smartproperty.features.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.network.SPApi
import com.myolwinoo.smartproperty.data.network.model.RegisterRequest
import com.myolwinoo.smartproperty.utils.InputValidator
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

    var name by mutableStateOf(TextFieldValue(""))
        private set
    var username by mutableStateOf(TextFieldValue(""))
        private set
    var email by mutableStateOf(TextFieldValue(""))
        private set
    var password by mutableStateOf(TextFieldValue(""))
        private set
    var confirmPassword by mutableStateOf(TextFieldValue(""))
        private set
    var phone by mutableStateOf(TextFieldValue(""))
        private set
    var address by mutableStateOf(TextFieldValue(""))
        private set
    var isLoading by mutableStateOf(false)
        private set
    val isRegisterEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { username.text },
        snapshotFlow { email.text },
        snapshotFlow { password.text },
        snapshotFlow { confirmPassword.text },
    ) { username, email, password, confirmPassword ->
        username.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    var showRegisterError by mutableStateOf(false)
        private set

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    fun onNameChange(value: TextFieldValue) {
        name = value
    }

    fun onUsernameChange(value: TextFieldValue) {
        username = value
    }

    fun onPhoneChange(value: TextFieldValue) {
        phone = value
    }

    fun onAddressChange(value: TextFieldValue) {
        address = value
    }

    fun onEmailChange(value: TextFieldValue) {
        emailError = null
        email = value
    }

    fun onPasswordChange(value: TextFieldValue) {
        passwordError = null
        confirmPasswordError = null
        password = value
    }

    fun onConfirmPasswordChange(value: TextFieldValue) {
        confirmPasswordError = null
        confirmPassword = value
    }

    fun register() {
        viewModelScope.launch {
            var hasError = false
            if (!InputValidator.isValidEmail(email.text)) {
                emailError = "Invalid email"
                hasError = true
            }

            if (!InputValidator.isValidPassword(password.text)) {
                passwordError = "Invalid password"
                hasError = true
            }

            if (password.text != confirmPassword.text) {
                confirmPasswordError = "Passwords do not match"
                hasError = true
            }

            if (hasError) {
                return@launch
            }

            isLoading = true
            spApi.register(
                RegisterRequest(
                    email = email.text,
                    password = password.text,
                    confirmPassword = confirmPassword.text,
                    name = name.text,
                    username = username.text,
                    phone = phone.text,
                    address = address.text
                )
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