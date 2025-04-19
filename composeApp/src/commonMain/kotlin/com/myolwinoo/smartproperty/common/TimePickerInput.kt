@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import org.jetbrains.compose.resources.painterResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_clock

@Composable
internal fun TimePickerInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onTimeSelected: (TextFieldValue) -> Unit,
    label: String,
    isError: Boolean,
) {
    var showTimePicker by remember { mutableStateOf(false) }
//    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState()

    val interactionSource = remember {
        InputClickInteractionSource(onClick = { showTimePicker = true })
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                }) { Text("Cancel") }
            },
            confirmButton = {
                TextButton(onClick = {
                    val hour = timePickerState.hour.toString().padStart(2, '0')
                    val min = timePickerState.minute.toString().padStart(2, '0')
                    onTimeSelected(TextFieldValue("$hour:$min:00"))
                    showTimePicker = false
                }) { Text("OK") }
            },
            text = { TimePicker(state = timePickerState) }
        )
    }

    OutlinedTextField(
        modifier = modifier
            .clickable { showTimePicker = true },
        readOnly = true,
        value = value,
        onValueChange = {},
        maxLines = 1,
        singleLine = true,
        interactionSource = interactionSource,
        label = { Text(text = label) },
//        supportingText = { Text("Required*") },
        trailingIcon = {
            IconButton(onClick = { showTimePicker = true }) {
                Icon(painterResource(Res.drawable.ic_clock), contentDescription = "Select Time")
            }
        },
        isError = isError
    )
}