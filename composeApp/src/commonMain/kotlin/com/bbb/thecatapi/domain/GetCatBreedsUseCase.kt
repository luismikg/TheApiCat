package com.bbb.thecatapi.domain

import com.bbb.thecatapi.domain.model.BreedsModel

class GetCatBreedsUseCase(val repositoryCats: RepositoryCats) {

    suspend operator fun invoke(): List<BreedsModel> {
        return repositoryCats.getBreeds()
    }
}