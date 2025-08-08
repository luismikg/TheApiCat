package com.bbb.thecatapi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val repositoryDataBase: RepositoryDataBase) : ViewModel() {

    var itemSelected = repositoryDataBase
        .getBreedSelected()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    var favorites = repositoryDataBase
        .getFavorite()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun addFavorite(breedsModel: BreedsModel) {
        viewModelScope.launch {
            repositoryDataBase.upsertFavorite(
                id = breedsModel.id,
                name = breedsModel.name,
                temperament = breedsModel.temperament,
                imageUrl = breedsModel.image.url,
                origen = breedsModel.origen,
                description = breedsModel.description,
                wikipediaUrl = breedsModel.wikipediaUrl
            )
        }
    }

    fun removeFavorite(breedsModel: BreedsModel) {
        viewModelScope.launch {
            repositoryDataBase.removeFavorite(id = breedsModel.id)
        }
    }
}