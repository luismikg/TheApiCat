package com.bbb.thecatapi.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class SQLDriverFactory actual constructor(context: Any?) {
    actual fun getSQLDriver(): SqlDriver {

        return JdbcSqliteDriver(
            "jdbc:sqlite:Database.db"
        )
    }
}