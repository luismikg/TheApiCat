package com.bbb.thecatapi.data.database

import app.cash.sqldelight.db.SqlDriver

expect class SQLDriverFactory(context: Any? = null) {
    fun getSQLDriver(): SqlDriver
}
