package com.myolwinoo.smartproperty.features.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.SPPasswordTextField
import com.myolwinoo.smartproperty.common.SPTextField
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_confirm_password
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_login
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_password
import smartproperty.composeapp.generated.resources.label_register
import smartproperty.composeapp.generated.resources.message_register_error
import smartproperty.composeapp.generated.resources.message_register_part1
import smartproperty.composeapp.generated.resources.message_register_part2
import smartproperty.composeapp.generated.resources.title_register_error
import smartproperty.composeapp.generated.resources.title_welcome

@Serializable
object RegisterRoute

fun NavController.navigateRegister() {
    navigate(RegisterRoute)
}

fun NavGraphBuilder.registerScreen(
    onRegisterSuccess: () -> Unit,
    onLogin: () -> Unit,
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
            isLoading = viewModel.isLoading,
            onRegister = viewModel::register,
            onLogin = onLogin,
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
    isLoading: Boolean,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
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
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Spacer(
                Modifier.size(64.dp)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                text = stringResource(Res.string.title_welcome),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    append(stringResource(Res.string.message_register_part1))
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        withLink(LinkAnnotation.Clickable(
                            tag = "login",
                            linkInteractionListener = object : LinkInteractionListener {
                                override fun onClick(link: LinkAnnotation) {
                                    onLogin()
                                }
                            }
                        )) {
                            append(stringResource(Res.string.message_register_part2))
                        }
                    }
                },
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.xl)
            )
            SPTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = email,
                onValueChange = onEmailChange,
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_email)) },
                suffix = { Text(text = "@gmail.com") }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            SPPasswordTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = password,
                onValueChange = onPasswordChange,
                { Text(stringResource(Res.string.label_password)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            SPPasswordTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                { Text(stringResource(Res.string.label_confirm_password)) }
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Button(
                modifier = Modifier
                    .padding(
                        start = AppDimens.Spacing.xl,
                        end = AppDimens.Spacing.xl,
                        bottom = AppDimens.Spacing.xxl
                    )
                    .fillMaxWidth()
                    .widthIn(max = AppDimens.maxWidth),
                enabled = isRegisterEnabled,
                onClick = { onRegister() }
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
            onRegister = {},
            email = TextFieldValue(),
            onEmailChange = {},
            password = TextFieldValue(),
            onPasswordChange = {},
            confirmPassword = TextFieldValue(),
            onConfirmPasswordChange = {},
            isLoading = false,
            isRegisterEnabled = true,
            showRegisterError = false,
            dismissRegisterError = {},
            onLogin = {}
        )
    }
}