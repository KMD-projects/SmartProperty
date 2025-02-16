package com.myolwinoo.smartproperty.di

import org.koin.dsl.module

val appModule = module {
    includes(
        viewModelModule,
        dataModule,
        commonModule
    )
}