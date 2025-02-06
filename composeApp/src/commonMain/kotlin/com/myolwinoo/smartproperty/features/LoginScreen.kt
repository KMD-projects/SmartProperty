package com.myolwinoo.smartproperty.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.compose_multiplatform
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_login
import smartproperty.composeapp.generated.resources.label_password
import smartproperty.composeapp.generated.resources.label_register

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
            onLoginSuccess = viewModel::login,
            onRegisterClick = onRegisterClick
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
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
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
                            imageVector = if (visiblePassword) {
                                Icons.Filled.Home
                            } else {
                                Icons.Filled.Place
                            },
                            contentDescription = null
                        )
                    }
                }
            )
            Button(
                enabled = isLoginEnabled,
                onClick = { onLoginSuccess() }
            ) { Text(
                text = stringResource(Res.string.label_login)
            ) }

            Button(
                onClick = { onRegisterClick() }
            ) { Text(
                text = stringResource(Res.string.label_register)
            ) }
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        email = TextFieldValue(),
        onEmailChange = {},
        password = TextFieldValue(),
        onPasswordChange = {},
        isLoading = true,
        isLoginEnabled = true,
        onLoginSuccess = {},
        onRegisterClick = {}
    )
}