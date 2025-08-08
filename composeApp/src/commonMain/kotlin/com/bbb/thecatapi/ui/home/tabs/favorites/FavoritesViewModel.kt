package com.bbb.thecatapi.ui.home.tabs.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repositoryDataBase: RepositoryDataBase) : ViewModel() {

    fun addSelectedItem(breedsModel: BreedsModel) {
        viewModelScope.launch {
            repositoryDataBase.upsertBreedSelected(
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
}