package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_delete

@Composable
fun PropertyImageList(
    modifier: Modifier = Modifier,
    images: List<PropertyImage>,
    enableDelete: Boolean = false,
    onRemoveImage: (String) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    imageSize: Int = 100,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        contentPadding = contentPadding,
    ) {
        imageList(
            images = images,
            imageSize = imageSize,
            enableDelete = enableDelete,
            onRemoveImage = onRemoveImage
        )
    }
}

private fun LazyListScope.imageList(
    images: List<PropertyImage>,
    imageSize: Int,
    enableDelete: Boolean = false,
    onRemoveImage: (String) -> Unit = {},
) {
    itemsIndexed(
        items = images,
        key = { _, item -> item.imageId }
    ) {  index, item ->
        if (index != 0) {
            Spacer(Modifier.size(8.dp))
        }
        ImageItem(
            modifier = Modifier.size(imageSize.dp),
            item = item,
            enableDelete = enableDelete,
            onRemoveImage = onRemoveImage
        )
    }
}

@Composable
private fun ImageItem(
    modifier: Modifier = Modifier,
    item: PropertyImage,
    enableDelete: Boolean = false,
    onRemoveImage: (String) -> Unit = {},
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            model = item.imageData,
            contentDescription = "Property Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (enableDelete) {
            Icon(
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(bottomStartPercent = 50)
                    )
                    .padding(
                        start = 8.dp,
                        end = 4.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                    .clickable {
                        onRemoveImage(item.imageId)
                    }
                    .size(24.dp)

            )
        }
    }
}

@Preview()
@Composable
private fun ListPreview() {
    PropertyImageList(
        images = PreviewData.properties.first().images,
        enableDelete = true
    )
}