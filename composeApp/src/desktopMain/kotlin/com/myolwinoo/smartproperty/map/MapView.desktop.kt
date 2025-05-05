package com.myolwinoo.smartproperty.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.myolwinoo.smartproperty.design.theme.SPTheme

@Composable
actual fun MapView(
    modifier: Modifier,
    latitude: Double,
    longitude: Double,
    markerTitle: String?,
    markerSnippet: String?,
    zoom: Float
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Map is not currently supported on desktops.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}