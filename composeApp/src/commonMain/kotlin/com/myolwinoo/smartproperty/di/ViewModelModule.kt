package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.features.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
}