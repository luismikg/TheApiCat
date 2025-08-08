package com.bbb.thecatapi.domain

import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.ImageBreedsModel
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCatBreedsUseCaseTest {

    class FakeRepositoryCats : RepositoryCats {
        override suspend fun getBreeds(): List<BreedsModel> {
            return listOf(
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
        }

        override fun getPagingBreeds() = throw NotImplementedError()
    }

    @Test
    fun `invoke returns expected breeds list`() = kotlinx.coroutines.test.runTest {
        val getCatBreedsUseCase = GetCatBreedsUseCase(FakeRepositoryCats())
        val result = getCatBreedsUseCase()
        assertEquals(1, result.size)
        assertEquals("Bengal", result.first().name)
    }
}