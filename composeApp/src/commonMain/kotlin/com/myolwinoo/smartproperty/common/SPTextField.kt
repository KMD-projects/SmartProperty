package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.hint_password_requirements
import smartproperty.composeapp.generated.resources.visibility_off
import smartproperty.composeapp.generated.resources.visibility_on

@Composable
fun SPPasswordTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: @Composable (() -> Unit),
    errorText: String? = null,
    showSupportingText: Boolean = false,
) {
    var visiblePassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        label = label,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (visiblePassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(mask = '*')
        },
        isError = errorText != null,
        supportingText = {
            when {
                errorText != null -> {
                    Text(errorText)
                }
                showSupportingText -> {
                    Text(stringResource(Res.string.hint_password_requirements))
                }
            }
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
}