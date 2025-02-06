package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SPTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: (TextFieldValue) -> Unit,
    label: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = modifier
            .widthIn(max = 560.dp),
        value = value,
        onValueChange = onValueChange,
        label = label,
        suffix = suffix,
        maxLines = maxLines,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}