package com.bbb.thecatapi.domain

import androidx.paging.PagingData
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class GetCatBreedsPagingUseCaseTest {
    class FakeRepositoryCats : RepositoryCats {
        override suspend fun getBreeds() = throw NotImplementedError()

        override fun getPagingBreeds() = flowOf(PagingData.empty<BreedsModel>())
    }

    @Test
    fun `get the paging flow`() = runTest {
        val getCatBreedsPagingUseCase = GetCatBreedsPagingUseCase(FakeRepositoryCats())
        val result = getCatBreedsPagingUseCase()
        assertNotNull(result)
    }

    @Test
    fun `flow with some data`() = runTest {
        val sampleBreeds = listOf(
            BreedsModel(
                id = "beng",
                name = "Bengal",
                temperament = "Alert, Agile",
                image = ImageBreedsModel("https://bengal.com/bengal.jpg"),
                origen = "USA",
                description = "Spotted coat",
                wikipediaUrl = "https://bengal.com/bengal",
                isFavorite = false
            )
        )

        val fakeRepo = object : RepositoryCats {
            override suspend fun getBreeds() = throw NotImplementedError()
            override fun getPagingBreeds() = flowOf(PagingData.from(sampleBreeds))
        }

        val getCatBreedsPagingUseCase = GetCatBreedsPagingUseCase(fakeRepo)
        val flow = getCatBreedsPagingUseCase()

        flow.collect { pagingData ->
            // Something emit
            assertNotNull(pagingData)
        }
    }

    @Test
    fun `empty PagingData when no breeds results`() = runTest {
        val fakeRepo = object : RepositoryCats {
            override suspend fun getBreeds() = throw NotImplementedError()
            override fun getPagingBreeds(): Flow<PagingData<BreedsModel>> =
                flowOf(PagingData.from(emptyList()))
        }

        val useCase = GetCatBreedsPagingUseCase(fakeRepo)
        val result = useCase()

        result.collect { pagingData ->
            assertNotNull(pagingData)
        }
    }
}