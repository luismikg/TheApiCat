package com.bbb.thecatapi.data


//import app.cash.sqldelight.sqlite.driver.JdbcSqliteDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bbb.data.database.Database
import com.bbb.thecatapi.domain.RepositoryDataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RepositoryDataBaseImplTest {

    private fun createRepository(): RepositoryDataBase {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        val db = Database(driver)
        return RepositoryDataBaseImpl(db)
    }

    @Test
    fun `session starts empty then updates after insert`() = runBlocking {
        val repositoryDataBase = createRepository()

        val initial = repositoryDataBase.getSession().first()
        assertTrue(initial.isEmpty())

        repositoryDataBase.upsertSession("token")

        val after = repositoryDataBase.getSession().first()
        assertEquals(listOf("token"), after)
    }

    @Test
    fun `upsert and read favorites according the token`() = runBlocking {
        val repositoryDataBase = createRepository()
        repositoryDataBase.upsertSession("token")

        repositoryDataBase.upsertFavorite(
            id = "beng",
            name = "Bengal",
            temperament = "Alert, Agile",
            imageUrl = "https://bengal.com/bengal.jpg",
            origen = "USA",
            description = "Spotted coat",
            wikipediaUrl = "https://bengal.com/bengal"
        )

        val favorites = repositoryDataBase.getFavorite().first()
        assertEquals(1, favorites.size)
        assertEquals("beng", favorites.first().id)


        ///
        repositoryDataBase.deleteSession()
        repositoryDataBase.upsertSession("token_other")

        repositoryDataBase.upsertFavorite(
            id = "beng",
            name = "Bengal",
            temperament = "Alert, Agile",
            imageUrl = "https://bengal.com/bengal.jpg",
            origen = "USA",
            description = "Spotted coat",
            wikipediaUrl = "https://bengal.com/bengal"
        )

        repositoryDataBase.upsertFavorite(
            id = "aege",
            name = "Aegean",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            imageUrl = "https://aege.com/aege.jpg",
            origen = "Greece",
            description = "Spotted coat",
            wikipediaUrl = "https://aege.com/aege"
        )

        val favoritesOther = repositoryDataBase.getFavorite().first()
        assertEquals(2, favoritesOther.size)
        assertEquals("aege", favoritesOther.first().id)
        assertEquals("beng", favoritesOther[1].id)


        // Remove
        repositoryDataBase.removeFavorite("beng")
        //val empty = repositoryDataBase.getFavorite().first()
        //assertTrue(empty.isEmpty())
    }


    @Test
    fun `remove favorites according the token getting empty list`() = runBlocking {
        val repositoryDataBase = createRepository()
        repositoryDataBase.upsertSession("token_other")

        repositoryDataBase.upsertFavorite(
            id = "beng",
            name = "Bengal",
            temperament = "Alert, Agile",
            imageUrl = "https://bengal.com/bengal.jpg",
            origen = "USA",
            description = "Spotted coat",
            wikipediaUrl = "https://bengal.com/bengal"
        )

        repositoryDataBase.upsertFavorite(
            id = "aege",
            name = "Aegean",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            imageUrl = "https://aege.com/aege.jpg",
            origen = "Greece",
            description = "Spotted coat",
            wikipediaUrl = "https://aege.com/aege"
        )

        val favoritesOther = repositoryDataBase.getFavorite().first()
        assertEquals(2, favoritesOther.size)
        assertEquals("aege", favoritesOther.first().id)
        assertEquals("beng", favoritesOther[1].id)


        // Remove
        repositoryDataBase.removeFavorite("beng")
        repositoryDataBase.removeFavorite("aege")
        val empty = repositoryDataBase.getFavorite().first()
        assertTrue(empty.isEmpty())
    }


    @Test
    fun `upsert breed selected and read flow`() = runBlocking {
        val repositoryDataBase = createRepository()

        repositoryDataBase.upsertBreedSelected(
            id = "aege",
            name = "Aegean",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            imageUrl = "https://aege.com/aege.jpg",
            origen = "Greece",
            description = "Spotted coat",
            wikipediaUrl = "https://aege.com/aege"
        )

        val selected = repositoryDataBase.getBreedSelected().first()
        assertEquals(1, selected.size)
        assertEquals("aege", selected.first().id)
    }

    @Test
    fun `cache breeds insert and read`() = runBlocking {
        val repositoryDataBase = createRepository()

        repositoryDataBase.clearAllBreedsCache()
        val empty = repositoryDataBase.getAllBreedCache()
        assertTrue(empty.isEmpty())

        repositoryDataBase.upsertBreedCache(
            id = "siam",
            name = "Siamese",
            temperament = "Vocal",
            imageUrl = "u2",
            origen = "Thailand",
            description = "Talkative",
            wikipediaUrl = "https://wiki/siam"
        )

        val cached = repositoryDataBase.getAllBreedCache()
        assertEquals(1, cached.size)
        assertEquals("siam", cached.first().id)

        repositoryDataBase.clearAllBreedsCache()
        val cleared = repositoryDataBase.getAllBreedCache()
        assertTrue(cleared.isEmpty())
    }
}