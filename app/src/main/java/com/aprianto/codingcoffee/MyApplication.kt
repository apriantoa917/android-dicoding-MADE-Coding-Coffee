package com.aprianto.codingcoffee

import android.app.Application
import com.aprianto.codingcoffee.di.useCaseModule
import com.aprianto.codingcoffee.di.viewModelModule
import com.aprianto.core.di.databaseModule
import com.aprianto.core.di.networkModule
import com.aprianto.core.di.repositoryModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/* ini adalah sample perubahan untuk testing cirlce-CI */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}