package com.bbb.thecatapi.data.remote

import com.bbb.thecatapi.data.remote.response.BreedsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client: HttpClient) {

    suspend fun getBreeds(): ArrayList<BreedsResponse> {
        return client.get("breeds").body()
    }
}