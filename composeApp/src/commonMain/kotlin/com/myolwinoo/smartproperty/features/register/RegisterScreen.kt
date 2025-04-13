package com.myolwinoo.smartproperty.features.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import smartproperty.composeapp.generated.resources.label_address
import smartproperty.composeapp.generated.resources.label_confirm_password
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_name
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_password
import smartproperty.composeapp.generated.resources.label_phone
import smartproperty.composeapp.generated.resources.label_register
import smartproperty.composeapp.generated.resources.label_username
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
            name = viewModel.name,
            onNameChange = viewModel::onNameChange,
            username = viewModel.username,
            onUsernameChange = viewModel::onUsernameChange,
            phone = viewModel.phone,
            onPhoneChange = viewModel::onPhoneChange,
            address = viewModel.address,
            onAddressChange = viewModel::onAddressChange,
            email = viewModel.email,
            onEmailChange = viewModel::onEmailChange,
            emailError = viewModel.emailError,
            password = viewModel.password,
            onPasswordChange = viewModel::onPasswordChange,
            passwordError = viewModel.passwordError,
            confirmPassword = viewModel.confirmPassword,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            confirmPasswordError = viewModel.confirmPasswordError,
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
    name: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    username: TextFieldValue,
    onUsernameChange: (TextFieldValue) -> Unit,
    phone: TextFieldValue,
    onPhoneChange: (TextFieldValue) -> Unit,
    address: TextFieldValue,
    onAddressChange: (TextFieldValue) -> Unit,
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    emailError: String?,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    passwordError: String?,
    confirmPassword: TextFieldValue,
    onConfirmPasswordChange: (TextFieldValue) -> Unit,
    confirmPasswordError: String?,
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
                .verticalScroll(rememberScrollState())
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
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = username,
                onValueChange = onUsernameChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_username)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = name,
                onValueChange = onNameChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_name)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
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
                maxLines = 1,
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it) } },
                label = { Text(stringResource(Res.string.label_email)) }
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
                showSupportingText = passwordError != null,
                errorText = passwordError,
                label = { Text(stringResource(Res.string.label_password)) }
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
                showSupportingText = false,
                errorText = confirmPasswordError,
                label = { Text(stringResource(Res.string.label_confirm_password)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = phone,
                onValueChange = onPhoneChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_phone)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.m)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = address,
                onValueChange = onAddressChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                minLines = 4,
                label = { Text(stringResource(Res.string.label_address)) }
            )
            Spacer(
                Modifier.size(AppDimens.Spacing.xl)
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
            name = TextFieldValue(),
            onNameChange = {},
            username = TextFieldValue(),
            onUsernameChange = {},
            phone = TextFieldValue(),
            onPhoneChange = {},
            address = TextFieldValue(),
            onAddressChange = {},
            onRegister = {},
            email = TextFieldValue(),
            onEmailChange = {},
            emailError = null,
            password = TextFieldValue(),
            onPasswordChange = {},
            passwordError = null,
            confirmPassword = TextFieldValue(),
            onConfirmPasswordChange = {},
            confirmPasswordError = null,
            isLoading = false,
            isRegisterEnabled = true,
            showRegisterError = false,
            dismissRegisterError = {},
            onLogin = {}
        )
    }
}