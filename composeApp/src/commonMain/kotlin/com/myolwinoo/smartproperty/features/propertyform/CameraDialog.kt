package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_cancel
import smartproperty.composeapp.generated.resources.label_capture

@Composable
fun CameraDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onImageCaptured: (ByteArray?) -> Unit
) {
    val state = rememberPeekabooCameraState(onCapture = {
        onImageCaptured(it)
        onDismissRequest()
    })
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box (
            modifier = modifier
                .fillMaxSize()
        ) {
            PeekabooCamera(
                state = state,
                modifier = Modifier.fillMaxSize(),
                permissionDeniedContent = {
                    Text(
                        text = "Camera permission denied",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = AppDimens.Spacing.xl,
                        end = AppDimens.Spacing.xl,
                        bottom = AppDimens.Spacing.xl
                    ),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.l)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { onDismissRequest() }
                ) {
                    Text(
                        text = stringResource(Res.string.label_cancel)
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { state.capture() },
                ) {
                    Text(
                        text = stringResource(Res.string.label_capture)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CameraDialogPreview() {
    SPTheme {
        CameraDialog(
            onDismissRequest = {
                // Do nothing
            },
            onImageCaptured = { byteArray -> }
        )
    }
}