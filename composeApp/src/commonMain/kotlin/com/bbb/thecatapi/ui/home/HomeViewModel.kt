package com.bbb.thecatapi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.GetCatBreedsPagingUseCase
import com.bbb.thecatapi.domain.RepositoryDataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCatBreedsPagingUseCase: GetCatBreedsPagingUseCase,
    repositoryDataBase: RepositoryDataBase
) :
    ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    var favorites = repositoryDataBase
        .getFavorite()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    init {
        getCatBreedsPaging()
    }

    fun refresh() {
        getCatBreedsPaging()
    }

    fun getCatBreedsPaging() {
        viewModelScope.launch {
            _state.update { s ->
                s.copy(
                    breedsModel = getCatBreedsPagingUseCase.invoke()
                )
            }
        }
    }
}