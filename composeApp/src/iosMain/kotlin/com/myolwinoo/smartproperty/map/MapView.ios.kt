package com.myolwinoo.smartproperty.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.myolwinoo.smartproperty.mapViewController
import platform.UIKit.UIViewController

@Composable
actual fun MapView(
    modifier: Modifier,
    latitude: Double,
    longitude: Double,
    markerTitle: String?,
    markerSnippet: String?,
    zoom: Float
) {
    UIKitViewController(
        factory = {
            val viewController: UIViewController = mapViewController.invoke(
                latitude,
                longitude,
                zoom,
                markerTitle,
                markerSnippet,
            )

            return@UIKitViewController viewController
        },
        modifier = modifier.fillMaxSize(),
    )
}