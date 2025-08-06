package com.bbb.thecatapi.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(apiKey: String, configuration: KoinAppDeclaration? = null) {
    startKoin {
        configuration?.invoke(this)
        modules(dataModule(apiKey = apiKey), domainModule, uiModule, getCoreDataBaseModule())
    }
}