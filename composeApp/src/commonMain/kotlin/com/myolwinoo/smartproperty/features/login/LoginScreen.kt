package com.myolwinoo.smartproperty.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.SPTextField
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.compose_multiplatform
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_login
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_password
import smartproperty.composeapp.generated.resources.label_register
import smartproperty.composeapp.generated.resources.message_login_error
import smartproperty.composeapp.generated.resources.title_login_error
import smartproperty.composeapp.generated.resources.visibility_off
import smartproperty.composeapp.generated.resources.visibility_on

@Serializable
object LoginRoute

fun NavController.navigateLogin() {
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    composable<LoginRoute> {
        val viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
        val isLoginEnabled = viewModel.isLoginEnabled.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    "login_success" -> onLoginSuccess()
                }
            }
        }

        Screen(
            email = viewModel.email,
            onEmailChange = viewModel::onEmailChange,
            password = viewModel.password,
            onPasswordChange = viewModel::onPasswordChange,
            isLoading = viewModel.isLoading,
            isLoginEnabled = isLoginEnabled.value,
            onLogin = viewModel::login,
            onRegisterClick = onRegisterClick,
            showLoginError = viewModel.showLoginError,
            dismissLoginError = viewModel::dismissLoginError
        )
    }
}

@Composable
private fun Screen(
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    isLoading: Boolean,
    isLoginEnabled: Boolean,
    onLogin: () -> Unit,
    onRegisterClick: () -> Unit,
    showLoginError: Boolean,
    dismissLoginError: () -> Unit,
) {
    if (showLoginError) {
        AlertDialog(
            title = {
                Text(stringResource(Res.string.title_login_error))
            },
            text = {
                Text(stringResource(Res.string.message_login_error))
            },
            confirmButton = {
                TextButton(
                    onClick = dismissLoginError
                ) {
                    Text(stringResource(Res.string.label_ok))
                }
            },
            onDismissRequest = dismissLoginError
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null
            )
            SPTextField(
                value = email,
                onValueChange = onEmailChange,
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_email)) },
                suffix = { Text(text = "@gmail.com") }
            )
            var visiblePassword by remember { mutableStateOf(false) }
            SPTextField(
                value = password,
                onValueChange = onPasswordChange,
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_password)) },
                visualTransformation = if (visiblePassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation(mask = '*')
                },
                trailingIcon = {
                    IconButton(onClick = { visiblePassword = !visiblePassword }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = if (visiblePassword) {
                                Res.drawable.visibility_on
                            } else {
                                Res.drawable.visibility_off
                            }.let { painterResource(it) },
                            contentDescription = null
                        )
                    }
                }
            )
            Button(
                enabled = isLoginEnabled,
                onClick = { onLogin() }
            ) {
                Text(
                    text = stringResource(Res.string.label_login)
                )
            }

            Button(
                onClick = { onRegisterClick() }
            ) {
                Text(
                    text = stringResource(Res.string.label_register)
                )
            }
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            email = TextFieldValue(),
            onEmailChange = {},
            password = TextFieldValue(),
            onPasswordChange = {},
            isLoading = true,
            isLoginEnabled = true,
            onLogin = {},
            onRegisterClick = {},
            showLoginError = true,
            dismissLoginError = {}
        )
    }
}