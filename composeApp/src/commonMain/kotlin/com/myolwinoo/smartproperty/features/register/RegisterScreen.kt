package com.myolwinoo.smartproperty.features.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_register
import smartproperty.composeapp.generated.resources.message_register_error
import smartproperty.composeapp.generated.resources.title_register_error

@Serializable
object RegisterRoute

fun NavController.navigateRegister() {
    navigate(RegisterRoute)
}

fun NavGraphBuilder.registerScreen(
    onRegisterSuccess: () -> Unit
) {
    composable<RegisterRoute> {
        val viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
        val isRegisterEnabled = viewModel.isRegisterEnabled.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    "register_success" -> onRegisterSuccess()
                }
            }
        }

        Screen(
            email = viewModel.email,
            onEmailChange = viewModel::onEmailChange,
            password = viewModel.password,
            onPasswordChange = viewModel::onPasswordChange,
            confirmPassword = viewModel.confirmPassword,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            isRegisterEnabled = isRegisterEnabled.value,
            onRegister = viewModel::register,
            showRegisterError = viewModel.showRegisterError,
            dismissRegisterError = viewModel::dismissRegisterError
        )
    }
}

@Composable
private fun Screen(
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    confirmPassword: TextFieldValue,
    onConfirmPasswordChange: (TextFieldValue) -> Unit,
    isRegisterEnabled: Boolean,
    onRegister: () -> Unit,
    showRegisterError: Boolean,
    dismissRegisterError: () -> Unit,
) {
    if (showRegisterError) {
        AlertDialog(
            title = {
                Text(stringResource(Res.string.title_register_error))
            },
            text = {
                Text(stringResource(Res.string.message_register_error))
            },
            confirmButton = {
                TextButton(
                    onClick = dismissRegisterError
                ) {
                    Text(stringResource(Res.string.label_ok))
                }
            },
            onDismissRequest = dismissRegisterError
        )
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                enabled = isRegisterEnabled,
                onClick = {
                    onRegister()
                }
            ) {
                Text(
                    text = stringResource(Res.string.label_register)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            onRegister = {},
            email = TextFieldValue(),
            onEmailChange = {},
            password = TextFieldValue(),
            onPasswordChange = {},
            confirmPassword = TextFieldValue(),
            onConfirmPasswordChange = {},
            isRegisterEnabled = true,
            showRegisterError = true,
            dismissRegisterError = {}
        )
    }
}