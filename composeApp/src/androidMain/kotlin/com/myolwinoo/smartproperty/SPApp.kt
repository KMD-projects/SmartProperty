package com.myolwinoo.smartproperty

import android.app.Application
import com.myolwinoo.smartproperty.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SPApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SPApp)
            androidLogger()
            modules(appModule)
        }
    }
}