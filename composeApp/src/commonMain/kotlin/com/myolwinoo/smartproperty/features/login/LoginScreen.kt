package com.myolwinoo.smartproperty.features.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_login
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_password
import smartproperty.composeapp.generated.resources.message_login_error
import smartproperty.composeapp.generated.resources.message_login_part1
import smartproperty.composeapp.generated.resources.message_login_part2
import smartproperty.composeapp.generated.resources.title_login_error
import smartproperty.composeapp.generated.resources.title_welcome

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
                    LoginEvent.LoginSuccess -> onLoginSuccess()
                }
            }
        }

        Screen(
            email = viewModel.email,
            onEmailChange = viewModel::onEmailChange,
            emailError = viewModel.emailError,
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
    emailError: String?,
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = AppDimens.maxWidth)
                    .fillMaxSize()
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
                        append(stringResource(Res.string.message_login_part1))
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            withLink(
                                LinkAnnotation.Clickable(
                                    tag = "register",
                                    linkInteractionListener = object : LinkInteractionListener {
                                        override fun onClick(link: LinkAnnotation) {
                                            onRegisterClick()
                                        }
                                    }
                                )) {
                                append(stringResource(Res.string.message_login_part2))
                            }
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(
                    Modifier.size(AppDimens.Spacing.xl)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = AppDimens.Spacing.xl)
                        .fillMaxWidth(),
                    value = email,
                    onValueChange = onEmailChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    isError = emailError != null,
                    maxLines = 1,
                    label = { Text(stringResource(Res.string.label_email)) },
                    supportingText = { emailError?.let { Text(it) } }
                )
                Spacer(
                    Modifier.size(AppDimens.Spacing.m)
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
                    label = { Text(stringResource(Res.string.label_password)) }
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
                        .fillMaxWidth(),
                    enabled = isLoginEnabled,
                    onClick = { onLogin() }
                ) {
                    Text(
                        text = stringResource(Res.string.label_login)
                    )
                }
            }

            if (isLoading) {
                LoadingOverlay()
            }
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
            emailError = null,
            password = TextFieldValue(),
            onPasswordChange = {},
            isLoading = false,
            isLoginEnabled = true,
            onLogin = {},
            onRegisterClick = {},
            showLoginError = true,
            dismissLoginError = {}
        )
    }
}