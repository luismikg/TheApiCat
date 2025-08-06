package com.bbb.thecatapi.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bbb.data.database.Database
import com.bbb.thecatapi.domain.RepositoryDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class RepositoryDataBaseImpl(
    private val database: Database
) : RepositoryDataBase {

    override fun getSession(): Flow<List<String>> {
        return database.databaseQueries
            .getSession()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun upsertSession(token: String) {
        database.databaseQueries.insertSession(token = token)
    }

    override suspend fun deleteSession() {
        database.databaseQueries.delateSession()
    }
}
