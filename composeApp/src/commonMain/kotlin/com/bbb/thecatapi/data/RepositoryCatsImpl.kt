package com.bbb.thecatapi.data

import com.bbb.thecatapi.data.remote.ApiService
import com.bbb.thecatapi.domain.RepositoryCats
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel

class RepositoryCatsImpl(private val api: ApiService) : RepositoryCats {
    override suspend fun getBreeds(): List<BreedsModel> {
        return api.getBreeds().map { breedsResponse ->
            BreedsModel(
                name = breedsResponse.name,
                temperament = breedsResponse.temperament,
                image = ImageBreedsModel(
                    url = breedsResponse.image.url
                )
            )
        }
    }
}
