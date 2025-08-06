package com.bbb.thecatapi.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bbb.data.database.Database

actual class SQLDriverFactory actual constructor(context: Any?) {

    val appContext = context as Context

    actual fun getSQLDriver(): SqlDriver {
        return AndroidSqliteDriver(
            Database.Schema,
            appContext,
            "Database.db"
        )
    }
}