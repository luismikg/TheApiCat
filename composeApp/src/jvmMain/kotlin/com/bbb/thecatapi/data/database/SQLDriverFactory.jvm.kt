package com.bbb.thecatapi.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bbb.data.database.Database

actual class SQLDriverFactory actual constructor(context: Any?) {
    actual fun getSQLDriver(): SqlDriver {

        val driver = JdbcSqliteDriver(
            "jdbc:sqlite:Database.db"
        )
        // Crear el esquema de la base de datos
        // El método `create` ejecuta las sentencias SQL
        Database.Schema.create(driver)
        return driver
    }
}