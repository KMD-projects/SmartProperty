package com.preat.peekaboo.ui.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@androidx.compose.runtime.Composable
actual fun PeekabooCamera(
    modifier: androidx.compose.ui.Modifier,
    cameraMode: com.preat.peekaboo.ui.camera.CameraMode,
    captureIcon: @androidx.compose.runtime.Composable ((() -> Unit) -> Unit),
    convertIcon: @androidx.compose.runtime.Composable ((() -> Unit) -> Unit),
    progressIndicator: @androidx.compose.runtime.Composable (() -> Unit),
    onCapture: (ByteArray?) -> Unit,
    onFrame: ((ByteArray) -> Unit)?,
    permissionDeniedContent: @androidx.compose.runtime.Composable (() -> Unit)
) {
    Box(modifier)
}

@Composable
actual fun PeekabooCamera(
    state: PeekabooCameraState,
    modifier: Modifier,
    permissionDeniedContent: @Composable () -> Unit,
) {
    Box(modifier)
}