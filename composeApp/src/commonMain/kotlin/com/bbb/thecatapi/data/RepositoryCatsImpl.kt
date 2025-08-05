package com.bbb.thecatapi.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bbb.thecatapi.data.remote.ApiService
import com.bbb.thecatapi.data.remote.paging.BreedsPagingSource
import com.bbb.thecatapi.domain.RepositoryCats
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
import kotlinx.coroutines.flow.Flow

class RepositoryCatsImpl(
    private val api: ApiService,
    private val breedsPagingSource: BreedsPagingSource
) : RepositoryCats {

    companion object {
        const val MAX_ITEMS_PAGER = 10
        const val PREFETCH_DISTANCE = 8
    }

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

    override fun getPagingBreeds(): Flow<PagingData<BreedsModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS_PAGER, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { breedsPagingSource }
        ).flow
    }
}
