package com.bbb.thecatapi.domain.model

data class BreedsModel(
    val id: String,
    val name: String,
    val temperament: String,
    val image: ImageBreedsModel
)
