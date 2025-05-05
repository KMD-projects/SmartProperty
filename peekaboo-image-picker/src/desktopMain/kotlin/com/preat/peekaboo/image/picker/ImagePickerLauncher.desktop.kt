package com.preat.peekaboo.image.picker

import androidx.compose.runtime.remember

@androidx.compose.runtime.Composable
actual fun rememberImagePickerLauncher(
    selectionMode: SelectionMode,
    scope: kotlinx.coroutines.CoroutineScope,
    resizeOptions: ResizeOptions,
    filterOptions: FilterOptions,
    onResult: (List<ByteArray>) -> Unit
): ImagePickerLauncher {
    return remember {
        ImagePickerLauncher(
            selectionMode = selectionMode,
            onLaunch = {},
        )
    }
}

actual class ImagePickerLauncher actual constructor(
    selectionMode: SelectionMode,
    private val onLaunch: () -> Unit,
) {
    actual fun launch() {
        onLaunch()
    }
}