package com.bbb.thecatapi.ui.home.tabs.online

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.GetCatBreedsPagingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnlineViewModel(private val getCatBreedsPagingUseCase: GetCatBreedsPagingUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(OnlineState())
    val state: StateFlow<OnlineState> = _state

    init {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    breedsModel = getCatBreedsPagingUseCase.invoke()
                )
            }
        }
    }
}
