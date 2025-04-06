package com.myolwinoo.smartproperty.features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.data.model.RequisitionStatus
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_become_landlord
import smartproperty.composeapp.generated.resources.label_request_landlord_again
import smartproperty.composeapp.generated.resources.label_request_landlord_pending
import smartproperty.composeapp.generated.resources.label_user_landlord
import smartproperty.composeapp.generated.resources.label_user_renter
import smartproperty.composeapp.generated.resources.label_request_landlord_rejected

@Composable
fun LandlordStatus(
    modifier: Modifier = Modifier,
    profile: User,
    onBecomeLandlord: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(profile.role) {
            UserRole.RENTER -> {
                when(val status = profile.requisitionStatus) {
                    RequisitionStatus.PENDING -> {
                        Text(
                            text = stringResource(Res.string.label_request_landlord_pending),
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                    RequisitionStatus.REJECTED -> {
                        Text(
                            text = stringResource(Res.string.label_request_landlord_rejected),
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = AppDimens.maxWidth),
                            onClick = { onBecomeLandlord() },
                        ) {
                            Text(
                                text = stringResource(Res.string.label_request_landlord_again)
                            )
                        }
                    }
                    null -> {
                        Text(
                            text = stringResource(Res.string.label_user_renter),
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = AppDimens.maxWidth),
                            onClick = { onBecomeLandlord() },
                        ) {
                            Text(
                                text = stringResource(Res.string.label_become_landlord)
                            )
                        }
                    }
                }
            }
            UserRole.LANDLORD -> {
                Text(
                    text = stringResource(Res.string.label_user_landlord),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun LandlordStatusPendingPreview() {
    SPTheme {
        LandlordStatus(
            profile = PreviewData.user.copy(
                role = UserRole.RENTER,
                requisitionStatus = RequisitionStatus.PENDING
            ),
            onBecomeLandlord = {}
        )
    }
}

@Preview
@Composable
private fun LandlordStatusRejectedPreview() {
    SPTheme {
        LandlordStatus(
            profile = PreviewData.user.copy(
                role = UserRole.RENTER,
                requisitionStatus = RequisitionStatus.REJECTED
            ),
            onBecomeLandlord = {}
        )
    }
}

@Preview
@Composable
private fun LandlordStatusPreview() {
    SPTheme {
        LandlordStatus(
            profile = PreviewData.user.copy(
                role = UserRole.RENTER,
                requisitionStatus = null
            ),
            onBecomeLandlord = {}
        )
    }
}

@Preview
@Composable
private fun LandlordStatusLandlordPreview() {
    SPTheme {
        LandlordStatus(
            profile = PreviewData.user.copy(
                role = UserRole.LANDLORD
            ),
            onBecomeLandlord = {}
        )
    }
}