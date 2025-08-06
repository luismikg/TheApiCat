package com.bbb.thecatapi.di

import app.cash.sqldelight.db.SqlDriver
import com.bbb.data.database.Database
import com.bbb.thecatapi.data.database.SQLDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getCoreDataBaseModule(): Module {
    return module {
        single { SQLDriverFactory().getSQLDriver() }
        single { Database.invoke(get<SqlDriver>()) }
    }
}