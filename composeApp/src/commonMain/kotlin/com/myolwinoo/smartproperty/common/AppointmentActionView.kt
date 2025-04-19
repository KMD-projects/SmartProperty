package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppointmentActionView(
    modifier: Modifier = Modifier,
    action: AppointmentAction,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        when(action) {
            AppointmentAction.ACCEPT_REJECT -> {
                FilledTonalButton(
                    onClick = { onDecline() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    Text("Decline")
                }
                Spacer(
                    Modifier.size(AppDimens.Spacing.m)
                )
                FilledTonalButton(
                    onClick = { onAccept() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Text("Accept")
                }
            }
            AppointmentAction.CANCEL -> {
                FilledTonalButton(
                    onClick = { onCancel() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    Text("Cancel")
                }
            }
            AppointmentAction.NONE -> {}
        }
    }
}

@Preview
@Composable
private fun AppointmentActionAcceptRejectPreview() {
    SPTheme {
        AppointmentActionView(
            action = AppointmentAction.ACCEPT_REJECT,
            onAccept = {},
            onDecline = {},
            onCancel = {}
        )
    }
}

@Preview
@Composable
private fun AppointmentActionCancelPreview() {
    SPTheme {
        AppointmentActionView(
            action = AppointmentAction.CANCEL,
            onAccept = {},
            onDecline = {},
            onCancel = {}
        )
    }
}

@Preview
@Composable
private fun AppointmentActionNonePreview() {
    SPTheme {
        AppointmentActionView(
            action = AppointmentAction.NONE,
            onAccept = {},
            onDecline = {},
            onCancel = {}
        )
    }
}