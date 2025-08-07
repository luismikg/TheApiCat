package com.bbb.thecatapi.domain

import kotlinx.coroutines.flow.Flow

interface RepositoryDataBase {
    fun getSession(): Flow<List<String>>
    suspend fun upsertSession(token: String)
    suspend fun deleteSession()
    suspend fun clearAllBreeds()
    suspend fun upsertBreed(id: String, name: String, temperament: String, imageUrl: String)
}