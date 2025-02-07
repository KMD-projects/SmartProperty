package com.myolwinoo.smartproperty

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.account_circle
import smartproperty.composeapp.generated.resources.favorite
import smartproperty.composeapp.generated.resources.label_explore
import smartproperty.composeapp.generated.resources.label_profile
import smartproperty.composeapp.generated.resources.label_wishlist
import smartproperty.composeapp.generated.resources.search

enum class AppDestinations(
    val label: StringResource,
    val icon: DrawableResource,
    val contentDescription: StringResource
) {
    Explore(Res.string.label_explore, Res.drawable.search, Res.string.label_explore),
    WISHLISTS(Res.string.label_wishlist, Res.drawable.favorite, Res.string.label_wishlist),
    PROFILE(Res.string.label_profile, Res.drawable.account_circle, Res.string.label_profile),
}