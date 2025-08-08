package com.bbb.thecatapi.data.remote

import com.bbb.thecatapi.data.remote.response.BreedsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface IApiService {
    suspend fun getBreeds(): ArrayList<BreedsResponse>
    suspend fun getPagingBreeds(page: Int, limit: Int): ArrayList<BreedsResponse>
}

class ApiService(private val client: HttpClient) : IApiService {

    override suspend fun getBreeds(): ArrayList<BreedsResponse> {
        return client.get("breeds").body()
    }

    override suspend fun getPagingBreeds(page: Int, limit: Int): ArrayList<BreedsResponse> {
        return client.get("breeds") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
}