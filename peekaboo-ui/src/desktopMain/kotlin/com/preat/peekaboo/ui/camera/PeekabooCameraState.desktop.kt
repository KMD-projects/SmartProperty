package com.preat.peekaboo.ui.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
actual class PeekabooCameraState(
    cameraMode: CameraMode,
    internal var onFrame: ((frame: ByteArray) -> Unit)?,
    internal var onCapture: (ByteArray?) -> Unit,
) {

    actual var isCameraReady: Boolean by mutableStateOf(false)

    internal var triggerCaptureAnchor: (() -> Unit)? = null

    actual var isCapturing: Boolean by mutableStateOf(false)

    actual var cameraMode: CameraMode by mutableStateOf(cameraMode)

    actual fun toggleCamera() {
        cameraMode = cameraMode.inverse()
    }

    actual fun capture() {
        isCapturing = true
        triggerCaptureAnchor?.invoke()
    }

    companion object {
        fun saver(
            onFrame: ((frame: ByteArray) -> Unit)?,
            onCapture: (ByteArray?) -> Unit,
        ): Saver<PeekabooCameraState, Int> {
            return Saver(
                save = { -1 },
                restore = {
                    PeekabooCameraState(
                        cameraMode = cameraModeFromId(it),
                        onFrame = onFrame,
                        onCapture = onCapture,
                    )
                },
            )
        }
    }
}

@Composable
actual fun rememberPeekabooCameraState(
    initialCameraMode: CameraMode,
    onFrame: ((ByteArray) -> Unit)?,
    onCapture: (ByteArray?) -> Unit
): PeekabooCameraState {
    return rememberSaveable(
        saver = PeekabooCameraState.saver(onFrame, onCapture),
    ) { PeekabooCameraState(initialCameraMode, onFrame, onCapture) }.apply {
        this.onFrame = onFrame
        this.onCapture = onCapture
    }
}