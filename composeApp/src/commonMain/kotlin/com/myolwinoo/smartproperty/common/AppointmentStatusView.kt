package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppointmentStatusView(
    modifier: Modifier = Modifier,
    status: AppointmentStatus
) {
    when (status) {
        AppointmentStatus.PENDING -> {
            SuggestionChip(
                modifier = modifier,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                shape = RoundedCornerShape(percent = 50),
                onClick = {},
                label = { Text(text = status.rawValue.capitalize()) },
            )
        }

        AppointmentStatus.ACCEPTED -> {
            SuggestionChip(
                modifier = modifier,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(percent = 50),
                onClick = {},
                label = { Text(text = status.rawValue.capitalize()) },
            )
        }

        AppointmentStatus.REJECTED -> {
            SuggestionChip(
                modifier = modifier,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    labelColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                shape = RoundedCornerShape(percent = 50),
                onClick = {},
                label = { Text(text = status.rawValue.capitalize()) },
            )
        }

        AppointmentStatus.CANCELLED -> {
            SuggestionChip(
                modifier = modifier,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    labelColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                shape = RoundedCornerShape(percent = 50),
                onClick = {},
                label = { Text(text = status.rawValue.capitalize()) },
            )
        }
    }
}

@Preview
@Composable
private fun AppointmentStatusPendingPreview() {
    SPTheme {
        AppointmentStatusView(
            status = AppointmentStatus.PENDING
        )
    }
}

@Preview
@Composable
private fun AppointmentStatusAcceptedPreview() {
    SPTheme {
        AppointmentStatusView(
            status = AppointmentStatus.ACCEPTED
        )
    }
}

@Preview
@Composable
private fun AppointmentStatusRejectedPreview() {
    SPTheme {
        AppointmentStatusView(
            status = AppointmentStatus.REJECTED
        )
    }
}

@Preview
@Composable
private fun AppointmentStatusCancelledPreview() {
    SPTheme {
        AppointmentStatusView(
            status = AppointmentStatus.CANCELLED
        )
    }
}