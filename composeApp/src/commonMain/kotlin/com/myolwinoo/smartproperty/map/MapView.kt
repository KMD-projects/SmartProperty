package com.myolwinoo.smartproperty.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
    markerTitle: String? = null,
    markerSnippet: String? = null,
    zoom: Float = 15f
)