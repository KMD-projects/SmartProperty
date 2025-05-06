package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.ui.tooling.preview.Preview

fun LazyStaggeredGridScope.appointmentList(
    appointments: List<Appointment>,
    onAccept: (String) -> Unit,
    onDecline: (String) -> Unit,
    onCancel: (String) -> Unit
) {
    items(
        items = appointments,
        key = { it.id }
    ) {
        AppointmentItem(
            appointment = it,
            onAccept = { onAccept(it.id) },
            onDecline = { onDecline(it.id) },
            onCancel = { onCancel(it.id) }
        )
    }
}

@Composable
fun AppointmentItem(
    modifier: Modifier = Modifier,
    appointment: Appointment,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Spacing.l)
        ) {
            appointment.status?.let {
                AppointmentStatusView(
                    status = it,
                )
            }
            if (appointment.description.isNotBlank()) {
                Text(
                    text = "\"${appointment.description}\"",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Renter ${appointment.renterName} would like to make an appointment from ${appointment.fromDate} to ${appointment.toDate}.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            appointment.status?.let {
                AppointmentActionView(
                    action = appointment.action,
                    onAccept = onAccept,
                    onDecline = onDecline,
                    onCancel = onCancel
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppointmentItemPreview() {
    SPTheme {
        AppointmentItem(
            appointment = PreviewData.appointments.first()
                .copy(action = AppointmentAction.ACCEPT_REJECT),
            onAccept = {},
            onDecline = {},
            onCancel = {}
        )
    }
}