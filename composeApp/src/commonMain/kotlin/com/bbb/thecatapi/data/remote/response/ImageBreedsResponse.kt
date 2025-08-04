package com.bbb.thecatapi.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageBreedsResponse(
    val id: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val url: String = ""
)
