package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.favorite
import smartproperty.composeapp.generated.resources.favorite_filled
import smartproperty.composeapp.generated.resources.title_cancel_appointment
import smartproperty.composeapp.generated.resources.title_make_appointment
import smartproperty.composeapp.generated.resources.title_view_appointments

@Composable
fun PropertyDetailFooter(
    modifier: Modifier = Modifier,
    property: Property,
    userRole: UserRole?,
    onFavoriteClick: (String) -> Unit,
    navigateToAppointmentForm: () -> Unit,
    cancelAppointment: () -> Unit,
    viewAppointments: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        if (userRole == UserRole.LANDLORD || property.isOwnProperty) {
//            Button(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .widthIn(max = AppDimens.maxWidth),
//                onClick = { viewAppointments() },
//            ) {
//                Text(
//                    text = stringResource(Res.string.title_view_appointments)
//                )
//            }
        } else {
            val hasAppointment = when(property.appointmentStatus) {
                AppointmentStatus.PENDING,
                AppointmentStatus.ACCEPTED -> true
                AppointmentStatus.REJECTED,
                AppointmentStatus.CANCELLED,
                null -> false
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .widthIn(max = AppDimens.maxWidth),
                    onClick = {
                        if (hasAppointment) {
                            cancelAppointment()
                        } else {
                            navigateToAppointmentForm()
                        }
                    },
                    enabled = !hasAppointment
                ) {
                    Text(
                        text = stringResource(Res.string.title_make_appointment)
                    )
                }
                IconButton(
                    onClick = { onFavoriteClick(property.id) },
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(
                            if (property.isFavorite) Res.drawable.favorite_filled else Res.drawable.favorite
                        ),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        PropertyDetailFooter(
            property = PreviewData.properties.first(),
            userRole = UserRole.RENTER,
            onFavoriteClick = {},
            navigateToAppointmentForm = {},
            cancelAppointment = {},
            viewAppointments = {}
        )
    }
}

@Preview
@Composable
private fun PreviewOwn() {
    SPTheme {
        PropertyDetailFooter(
            property = PreviewData.properties.first()
                .copy(isOwnProperty = true),
            userRole = UserRole.LANDLORD,
            onFavoriteClick = {},
            navigateToAppointmentForm = {},
            cancelAppointment = {},
            viewAppointments = {}
        )
    }
}