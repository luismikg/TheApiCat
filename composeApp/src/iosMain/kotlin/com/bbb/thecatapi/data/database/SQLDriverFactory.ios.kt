package com.bbb.thecatapi.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bbb.data.database.Database

actual class SQLDriverFactory actual constructor(context: Any?) {
    actual fun getSQLDriver(): SqlDriver {

        return NativeSqliteDriver(
            Database.Schema,
            "Database.db"
        )
    }
}