package com.bbb.thecatapi.ui.home.tabs.online

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.domain.model.BreedsModel
import kotlinx.coroutines.launch

class OnlineViewModel(private val repositoryDataBase: RepositoryDataBase) :
    ViewModel() {

    fun addFavorite(breedsModel: BreedsModel) {
        viewModelScope.launch {
            repositoryDataBase.upsertFavorite(
                id = breedsModel.id,
                name = breedsModel.name,
                temperament = breedsModel.temperament,
                imageUrl = breedsModel.image.url
            )
        }
    }

    fun removeFavorite(breedsModel: BreedsModel) {
        viewModelScope.launch {
            repositoryDataBase.removeFavorite(id = breedsModel.id)
        }
    }
}
