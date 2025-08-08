package com.bbb.thecatapi.domain.model

data class FavoriteModel(
    val id: String,
    val name: String,
    val temperament: String,
    val imageUrl: String,
    var origen: String,
    var description: String,
    val wikipediaUrl: String,
)
