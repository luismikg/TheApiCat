package com.bbb.thecatapi.di

import com.bbb.thecatapi.data.RepositoryCatsImpl
import com.bbb.thecatapi.data.remote.ApiService
import com.bbb.thecatapi.data.remote.paging.BreedsPagingSource
import com.bbb.thecatapi.domain.RepositoryCats
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.thecatapi.com/v1"
                    parameters.append(
                        "api_key",
                        "live_6QPkyl7XR20V5vTrTcTQiQXB2pXjqBKZTc4TXldwPuqXCuaJiKEITzWLx1A47of2"
                    )
                }
            }
        }
    }

    //factory<ApiService>{ ApiService(get()) }
    factoryOf(::ApiService)
    factory<RepositoryCats> { RepositoryCatsImpl(get(), get()) }
    factoryOf(::BreedsPagingSource)
}