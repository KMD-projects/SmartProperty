package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    dateLabel: String,
    date: TextFieldValue,
    onDateChange: (TextFieldValue) -> Unit,
    isDateError: Boolean,
    timeLabel: String,
    time: TextFieldValue,
    onTimeChange: (TextFieldValue) -> Unit,
    isTimeError: Boolean,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.l)
    ) {
        DatePickerInput(
            modifier = Modifier.weight(1f),
            label = dateLabel,
            date = date,
            onDateChange = onDateChange,
            isError = isDateError
        )
        TimePickerInput(
            modifier = Modifier.weight(1f),
            label = timeLabel,
            value = time,
            onTimeSelected = onTimeChange,
            isError = isTimeError
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        DateTimePicker(
            dateLabel = "Date",
            date = TextFieldValue("2023-01-01"),
            onDateChange = {},
            isDateError = false,
            timeLabel = "Time",
            time = TextFieldValue("12:00"),
            onTimeChange = {},
            isTimeError = false
        )
    }
}