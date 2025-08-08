package com.bbb.thecatapi.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bbb.data.database.CatBreedsCache
import com.bbb.data.database.Database
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.FavoriteModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
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
        imageUrl: String,
        origen: String,
        description: String,
        wikipediaUrl: String
    ) {
        database.databaseQueries.insertCatBreedsCache(
            id = id,
            name = name,
            temperament = temperament,
            image_url = imageUrl,
            origen = origen,
            description = description,
            wikipedia_url = wikipediaUrl
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
                            imageUrl = favorite.image_url ?: "",
                            origen = favorite.origen,
                            description = favorite.description,
                            wikipediaUrl = favorite.wikipedia_url
                        )
                    }
                }
        }
    }

    override suspend fun upsertFavorite(
        id: String,
        name: String,
        temperament: String,
        imageUrl: String,
        origen: String,
        description: String,
        wikipediaUrl: String
    ) {
        val session = getSession().first().firstOrNull() ?: ""
        database.databaseQueries
            .upsertFavorite(
                id = id,
                token = session,
                name = name,
                temperament = temperament,
                image_url = imageUrl,
                origen = origen,
                description = description,
                wikipedia_url = wikipediaUrl
            )
    }

    override suspend fun removeFavorite(id: String) {
        val session = getSession().first().firstOrNull() ?: ""
        database.databaseQueries
            .deleteFavorite(token = session, id = id)
    }

    override suspend fun upsertBreedSelected(
        id: String,
        name: String,
        temperament: String,
        imageUrl: String,
        origen: String,
        description: String,
        wikipediaUrl: String
    ) {
        database.databaseQueries.clearAllCatBreedsSelected()
        database.databaseQueries.insertCatBreedsSelected(
            id = id,
            name = name,
            temperament = temperament,
            image_url = imageUrl,
            origen = origen,
            description = description,
            wikipedia_url = wikipediaUrl
        )
    }

    override fun getBreedSelected(): Flow<List<BreedsModel>> {
        return database.databaseQueries
            .getOneCatBreedSelected()
            .asFlow()
            .mapToList(context = Dispatchers.IO).map { list ->
                list.map { item ->
                    BreedsModel(
                        id = item.id,
                        name = item.name,
                        temperament = item.temperament,
                        image = ImageBreedsModel(url = item.image_url),
                        origen = item.origen,
                        description = item.description,
                        wikipediaUrl = item.wikipedia_url
                    )
                }
            }
    }
}
