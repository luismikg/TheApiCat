package com.bbb.thecatapi.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bbb.thecatapi.data.RepositoryCatsImpl
import com.bbb.thecatapi.data.remote.ApiService
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
import io.ktor.utils.io.errors.IOException

class BreedsPagingSource(
    private val apiService: ApiService,
    private val repositoryDataBase: RepositoryDataBase
) : PagingSource<Int, BreedsModel>() {
    override fun getRefreshKey(state: PagingState<Int, BreedsModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedsModel> {
        return try {
            val page = params.key ?: 0

            val response = apiService.getPagingBreeds(
                page = page, limit = RepositoryCatsImpl.MAX_ITEMS_PAGER
            )
            val breeds = response

            val prevKey = if (page > 0) -1 else null
            val nextKey = if (breeds.isNotEmpty()) page + 1 else null

            //Remove all cache
            if (page == 0) {
                repositoryDataBase.clearAllBreeds()
            }

            val result = LoadResult.Page(
                data = breeds.map { breedsResponse ->

                    //Save cache
                    repositoryDataBase.upsertBreed(
                        id = breedsResponse.id,
                        name = breedsResponse.name,
                        temperament = breedsResponse.temperament,
                        imageUrl = breedsResponse.image.url
                    )

                    BreedsModel(
                        id = breedsResponse.id,
                        name = breedsResponse.name,
                        temperament = breedsResponse.temperament,
                        image = ImageBreedsModel(
                            url = breedsResponse.image.url
                        )
                    )
                },
                prevKey = prevKey,
                nextKey = nextKey
            )

            result

        } catch (exception: IOException) {

            val breeds = repositoryDataBase.getAllBreed().map { catBreeds ->
                BreedsModel(
                    id = catBreeds.id,
                    name = catBreeds.name,
                    temperament = catBreeds.temperament,
                    image = ImageBreedsModel(
                        url = catBreeds.image_url ?: ""
                    )
                )
            }

            if (breeds.isEmpty()) {
                LoadResult.Error(exception)
            } else {
                LoadResult.Page(data = breeds, prevKey = null, nextKey = null)
            }
        }
    }
}