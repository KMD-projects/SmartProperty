package com.myolwinoo.smartproperty

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_home
import smartproperty.composeapp.generated.resources.label_profile
import smartproperty.composeapp.generated.resources.label_search

enum class AppDestinations(
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource
) {
    HOME(Res.string.label_home, Icons.Default.Home, Res.string.label_home),
    SEARCH(Res.string.label_search, Icons.Default.Search, Res.string.label_search),
    PROFILE(Res.string.label_profile, Icons.Default.AccountBox, Res.string.label_profile),
}