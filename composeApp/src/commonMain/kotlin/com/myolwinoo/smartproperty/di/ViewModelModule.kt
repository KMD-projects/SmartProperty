package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.features.explore.ExploreViewModel
import com.myolwinoo.smartproperty.features.login.LoginViewModel
import com.myolwinoo.smartproperty.features.register.RegisterViewModel
import com.myolwinoo.smartproperty.features.wishlists.WishlistsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ExploreViewModel)
    viewModelOf(::WishlistsViewModel)
}