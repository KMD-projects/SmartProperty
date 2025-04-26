package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_camera
import smartproperty.composeapp.generated.resources.ic_library
import smartproperty.composeapp.generated.resources.label_camera
import smartproperty.composeapp.generated.resources.label_images
import smartproperty.composeapp.generated.resources.label_library

@Composable
fun ImageChooser(
    modifier: Modifier = Modifier,
    sideSpacing: Dp,
    images: List<PropertyImage>,
    onAddImages: (List<ByteArray>) -> Unit,
    onRemoveImage: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    var openCamera by remember { mutableStateOf(false) }

    val multipleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Multiple(),
        scope = scope,
        onResult = { byteArrays ->
            onAddImages(byteArrays)
        }
    )

    if (openCamera) {
        CameraDialog(
            onDismissRequest = {
                openCamera = false
            },
            onImageCaptured = {
                it?.let {
                    onAddImages(listOf(it))
                }
            }
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimens.Spacing.s)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = sideSpacing),
            text = stringResource(Res.string.label_images),
            style = MaterialTheme.typography.titleMedium
        )
        if (images.isNotEmpty()) {
            Spacer(Modifier.size(AppDimens.Spacing.l))
        }
        PropertyImageList(
            contentPadding = PaddingValues(horizontal = sideSpacing),
            images = images,
            enableDelete = true,
            onRemoveImage = onRemoveImage
        )
        if (images.isNotEmpty()) {
            Spacer(Modifier.size(AppDimens.Spacing.l))
        }
        Row(
            modifier = Modifier
                .padding(horizontal = sideSpacing),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.l)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = { openCamera = true }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_camera),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(Res.string.label_camera)
                )
            }

            OutlinedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = { multipleImagePicker.launch() }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_library),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(Res.string.label_library)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        ImageChooser(
            images = PreviewData.properties.first().images,
            sideSpacing = AppDimens.Spacing.xl,
            onAddImages = {},
            onRemoveImage = {}
        )
    }
}