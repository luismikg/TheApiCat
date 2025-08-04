package com.bbb.thecatapi.ui.home.tabs.online

import androidx.paging.PagingData
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class OnlineState(
    val breedsModel: Flow<PagingData<BreedsModel>> = emptyFlow()
)