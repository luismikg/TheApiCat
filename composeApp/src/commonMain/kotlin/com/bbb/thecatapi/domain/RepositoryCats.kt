package com.bbb.thecatapi.domain

import com.bbb.thecatapi.domain.model.BreedsModel

interface RepositoryCats {
    suspend fun getBreeds(): List<BreedsModel>
}