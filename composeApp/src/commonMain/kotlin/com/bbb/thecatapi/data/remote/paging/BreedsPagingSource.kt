package com.bbb.thecatapi.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bbb.thecatapi.data.RepositoryCatsImpl
import com.bbb.thecatapi.data.remote.ApiService
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
import io.ktor.utils.io.errors.IOException

class BreedsPagingSource(private val apiService: ApiService) : PagingSource<Int, BreedsModel>() {
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

            LoadResult.Page(
                data = breeds.map { breedsResponse ->
                    BreedsModel(
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

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}