package com.bbb.thecatapi.data.remote

import com.bbb.thecatapi.data.remote.response.BreedsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val client: HttpClient) {

    suspend fun getBreeds(): ArrayList<BreedsResponse> {
        return client.get("breeds").body()
    }

    suspend fun getPagingBreeds(page: Int, limit: Int): ArrayList<BreedsResponse> {
        return client.get("breeds") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
}