package com.bbb.thecatapi.ui.home.tabs.online

import com.bbb.thecatapi.domain.model.BreedsModel

data class OnlineState(
    val isLoading: Boolean = false,
    val breedsModel: List<BreedsModel>? = null
)