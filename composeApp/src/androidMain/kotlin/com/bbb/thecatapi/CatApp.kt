package com.bbb.thecatapi

import android.app.Application
import com.bbb.thecatapi.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class CatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val apiKey = BuildKonfig.THE_CAT_API_KEY
        initKoin(
            apiKey = apiKey,
            configuration = {
                androidLogger(Level.INFO)
                androidContext(this@CatApp)
            }
        )
    }
}