package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.data.DataStoreProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {

    single {
        DataStoreProvider(androidApplication())
    }

}