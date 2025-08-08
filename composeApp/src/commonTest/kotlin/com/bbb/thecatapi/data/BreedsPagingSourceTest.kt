package com.bbb.thecatapi.data

import androidx.paging.PagingSource
import com.bbb.data.database.CatBreedsCache
import com.bbb.thecatapi.data.remote.IApiService
import com.bbb.thecatapi.data.remote.paging.BreedsPagingSource
import com.bbb.thecatapi.data.remote.response.BreedsResponse
import com.bbb.thecatapi.data.remote.response.ImageBreedsResponse
import com.bbb.thecatapi.domain.RepositoryDataBase
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BreedsPagingSourceTest {

    class RepositoryDataBaseTest : RepositoryDataBase {
        var cleared = false
        val cache = mutableListOf<CatBreedsCache>()

        override suspend fun clearAllBreedsCache() {
            cleared = true
        }

        override suspend fun upsertBreedCache(
            id: String, name: String, temperament: String, imageUrl: String,
            origen: String, description: String, wikipediaUrl: String
        ) {
            cache.add(
                CatBreedsCache(
                    id,
                    name,
                    temperament,
                    imageUrl,
                    origen,
                    description,
                    wikipediaUrl
                )
            )
        }

        override suspend fun getAllBreedCache(): List<CatBreedsCache> = cache.toList()

        // Métodos no usados en estos tests
        override fun getSession() = throw NotImplementedError()
        override suspend fun upsertSession(token: String) = Unit
        override suspend fun deleteSession() = Unit
        override fun getFavorite() = throw NotImplementedError()
        override suspend fun upsertFavorite(
            id: String,
            name: String,
            temperament: String,
            imageUrl: String,
            origen: String,
            description: String,
            wikipediaUrl: String
        ) = Unit

        override suspend fun removeFavorite(id: String) = Unit
        override suspend fun upsertBreedSelected(
            id: String,
            name: String,
            temperament: String,
            imageUrl: String,
            origen: String,
            description: String,
            wikipediaUrl: String
        ) = Unit

        override fun getBreedSelected() = throw NotImplementedError()
    }

    @Test
    fun `page 0 load so the cache and returns mach`() = runBlocking {
        val repositoryDataBaseTest = RepositoryDataBaseTest()
        val breeds = listOf(
            BreedsResponse(
                id = "beng",
                name = "Bengal",
                temperament = "Alert, Agile",
                image = ImageBreedsResponse(url = "https://bengal.com/bengal.jpg"),
                origin = "USA",
                description = "Spotted coat",
                wikipediaUrl = "https://bengal.com/bengal",
            )
        )
        val apiService = object : IApiService {
            override suspend fun getPagingBreeds(page: Int, limit: Int) = ArrayList(breeds)
            override suspend fun getBreeds() = arrayListOf<BreedsResponse>()
        }

        val source = BreedsPagingSource(apiService, repositoryDataBaseTest)
        val result = source.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue(repositoryDataBaseTest.cleared)
        assertEquals(1, result.data.size)
        assertEquals("beng", result.data.first().id)
        assertEquals(1, repositoryDataBaseTest.cache.size)
        assertEquals("Alert, Agile", repositoryDataBaseTest.cache.first().temperament)
    }

    @Test
    fun `page 0 reset cache`() = runBlocking {
        val repositoryDataBaseTest = RepositoryDataBaseTest()
        val breeds = listOf(
            BreedsResponse(
                id = "beng",
                name = "Bengal",
                temperament = "Alert, Agile",
                image = ImageBreedsResponse(url = "https://bengal.com/bengal.jpg"),
                origin = "USA",
                description = "Spotted coat",
                wikipediaUrl = "https://bengal.com/bengal",
            )
        )
        val apiService = object : IApiService {
            override suspend fun getPagingBreeds(page: Int, limit: Int) = ArrayList(breeds)
            override suspend fun getBreeds() = arrayListOf<BreedsResponse>()
        }

        val source = BreedsPagingSource(apiService, repositoryDataBaseTest)
        val result = source.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue(repositoryDataBaseTest.cleared)
    }

    @Test
    fun `page bigger than 0 does not clear cache`() = runBlocking {
        val repositoryDataBaseTest = RepositoryDataBaseTest()
        val breeds = listOf(
            BreedsResponse(
                id = "beng",
                name = "Bengal",
                temperament = "Alert, Agile",
                image = ImageBreedsResponse(url = "https://bengal.com/bengal.jpg"),
                origin = "USA",
                description = "Spotted coat",
                wikipediaUrl = "https://bengal.com/bengal",
            )
        )
        val apiService = object : IApiService {
            override suspend fun getPagingBreeds(page: Int, limit: Int) = ArrayList(breeds)
            override suspend fun getBreeds() = arrayListOf<BreedsResponse>()
        }

        val source = BreedsPagingSource(apiService, repositoryDataBaseTest)
        val result = source.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertTrue(!repositoryDataBaseTest.cleared)
    }

    @Test
    fun `IOException return cache returns`() = runBlocking {
        val repositoryDataBaseTest = RepositoryDataBaseTest()
        repositoryDataBaseTest.cache.add(
            CatBreedsCache(
                id = "fromCache",
                name = "Bengal",
                temperament = "Alert, Agile",
                image_url = "https://bengal.com/bengal.jpg",
                origen = "USA",
                description = "Spotted coat",
                wikipedia_url = "https://bengal.com/bengal",
            )
        )

        val apiService = object : IApiService {
            override suspend fun getPagingBreeds(page: Int, limit: Int): ArrayList<BreedsResponse> {
                throw IOException("fail")
            }

            override suspend fun getBreeds() = arrayListOf<BreedsResponse>()
        }

        val source = BreedsPagingSource(apiService, repositoryDataBaseTest)
        val result = source.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(1, result.data.size)
        assertEquals("fromCache", result.data.first().id)
    }
}
