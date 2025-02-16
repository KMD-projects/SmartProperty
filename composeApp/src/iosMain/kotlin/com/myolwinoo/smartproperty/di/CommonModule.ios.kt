package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.data.DataStoreProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {

    single {
        DataStoreProvider()
    }
}