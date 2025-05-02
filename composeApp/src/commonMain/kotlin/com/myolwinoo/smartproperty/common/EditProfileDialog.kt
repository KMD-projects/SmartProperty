package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_address
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_name
import smartproperty.composeapp.generated.resources.label_phone

@Composable
fun EditProfileDialog(
    name: TextFieldValue,
    email: TextFieldValue,
    phone: TextFieldValue,
    address: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    onEmailChange: (TextFieldValue) -> Unit,
    onPhoneChange: (TextFieldValue) -> Unit,
    onAddressChange: (TextFieldValue) -> Unit,
    emailError: String?,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = AppDimens.Spacing.xl)
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
                    value = phone,
                    onValueChange = onPhoneChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
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
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { onSubmit() }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        EditProfileDialog(
            onDismissRequest = {},
            onSubmit = {},
            name = TextFieldValue("John Doe"),
            email = TextFieldValue(""),
            phone = TextFieldValue("1234567890"),
            address = TextFieldValue("123 Main St, City, Country"),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onAddressChange = {},
            emailError = null
        )
    }
}
