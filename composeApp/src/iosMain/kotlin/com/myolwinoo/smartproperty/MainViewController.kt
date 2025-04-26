package com.myolwinoo.smartproperty

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(
    mapUIViewController: (
        latitude: Double,
        longitude: Double,
        zoom: Float,
        title: String?,
        snippet: String?,
    ) -> UIViewController
) = ComposeUIViewController {
    mapViewController = mapUIViewController
    App()
}

lateinit var mapViewController: (
    latitude: Double,
    longitude: Double,
    zoom: Float,
    title: String?,
    snippet: String?,
) -> UIViewController