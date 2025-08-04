package com.bbb.thecatapi.domain

import androidx.paging.PagingData
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.flow.Flow

interface RepositoryCats {
    suspend fun getBreeds(): List<BreedsModel>
    fun getPagingBreeds(): Flow<PagingData<BreedsModel>>
}