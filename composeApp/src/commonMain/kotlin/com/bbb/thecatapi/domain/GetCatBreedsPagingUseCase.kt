package com.bbb.thecatapi.domain

import androidx.paging.PagingData
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.flow.Flow

class GetCatBreedsPagingUseCase(val repositoryCats: RepositoryCats) {

    operator fun invoke(): Flow<PagingData<BreedsModel>> {
        return repositoryCats.getPagingBreeds()
    }
}