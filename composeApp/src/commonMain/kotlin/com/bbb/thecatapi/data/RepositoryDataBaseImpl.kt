package com.bbb.thecatapi.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bbb.data.database.CatBreedsCache
import com.bbb.data.database.Database
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.FavoriteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

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
        database.databaseQueries.deleteSession()
    }

    //CACHE
    override suspend fun clearAllBreedsCache() {
        database.databaseQueries.clearAllCatBreedsCache()
    }

    override suspend fun upsertBreedCache(
        id: String,
        name: String,
        temperament: String,
        imageUrl: String
    ) {
        database.databaseQueries.insertCatBreedsCache(
            id = id,
            name = name,
            temperament = temperament,
            image_url = imageUrl
        )
    }

    override suspend fun getAllBreedCache(): List<CatBreedsCache> {
        return database.databaseQueries
            .getAllCatBreedsCache()
            .executeAsList()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorite(): Flow<List<FavoriteModel>> {
        return getSession().flatMapLatest { sessionList ->
            val session = sessionList.firstOrNull() ?: ""
            database.databaseQueries
                .getFavorite(token = session)
                .asFlow()
                .mapToList(context = Dispatchers.IO).map { favoritesList ->
                    favoritesList.map { favorite ->
                        FavoriteModel(
                            id = favorite.id,
                            name = favorite.name,
                            temperament = favorite.temperament,
                            imageUrl = favorite.image_url ?: ""
                        )
                    }
                }
        }
    }

    override suspend fun upsertFavorite(
        id: String,
        name: String,
        temperament: String,
        imageUrl: String
    ) {
        val session = getSession().first().firstOrNull() ?: ""
        database.databaseQueries
            .upsertFavorite(
                id = id,
                token = session,
                name = name,
                temperament = temperament,
                image_url = imageUrl
            )
    }

    override suspend fun removeFavorite(id: String) {
        val session = getSession().first().firstOrNull() ?: ""
        database.databaseQueries
            .deleteFavorite(token = session, id = id)
    }
}
