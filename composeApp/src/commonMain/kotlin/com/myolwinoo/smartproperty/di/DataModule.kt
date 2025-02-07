package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.data.network.SPApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::SPApi)
}