package com.preat.peekaboo.ui.gallery

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalPeekabooGalleryApi
@Composable
actual fun PeekabooGallery(
    modifier: Modifier,
    state: GalleryPickerState,
    lazyGridState: LazyGridState,
    backgroundColor: Color,
    header: @Composable (() -> Unit),
    progressIndicator: @Composable (() -> Unit),
    permissionDeniedContent: @Composable (() -> Unit),
    onImageSelected: (ByteArray?) -> Unit
) {

}