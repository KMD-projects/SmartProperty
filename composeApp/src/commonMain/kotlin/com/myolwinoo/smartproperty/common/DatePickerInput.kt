@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.myolwinoo.smartproperty.utils.DateUtils
import org.jetbrains.compose.resources.painterResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_calendar

@Composable
fun DatePickerInput(
    modifier: Modifier = Modifier,
    label: String,
    date: TextFieldValue,
    onDateChange: (TextFieldValue) -> Unit,
    isError: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember {
        InputClickInteractionSource(onClick = { showDatePicker = true })
    }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = date,
            onValueChange = onDateChange,
            label = { Text(text = label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_calendar),
                        contentDescription = "Select date"
                    )
                }
            },
            interactionSource = interactionSource,
            isError = isError
        )

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
//                selectableDates = object : SelectableDates {
//                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//                        val localDate = Instant.fromEpochMilliseconds(utcTimeMillis)
//                            .toLocalDateTime(TimeZone.currentSystemDefault())
//                        val today = Clock.System.now().toEpochMilliseconds()
//                        return (localDate.dayOfWeek == selectableDay) && (utcTimeMillis > today)
//                    }
//                }
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            DateUtils.toDisplayDate(it)
                        }?.also { onDateChange(TextFieldValue(it)) }
                        showDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
}
