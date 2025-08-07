package com.bbb.thecatapi.domain

import com.bbb.data.database.CatBreedsCache
import com.bbb.thecatapi.domain.model.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface RepositoryDataBase {
    fun getSession(): Flow<List<String>>
    suspend fun upsertSession(token: String)
    suspend fun deleteSession()
    suspend fun clearAllBreedsCache()
    suspend fun upsertBreedCache(id: String, name: String, temperament: String, imageUrl: String)
    suspend fun getAllBreedCache(): List<CatBreedsCache>
    fun getFavorite(): Flow<List<FavoriteModel>>
    suspend fun upsertFavorite(id: String, name: String, temperament: String, imageUrl: String)
    suspend fun removeFavorite(id: String)
}