package com.bbb.thecatapi.ui.home.tabs.online

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.GetCatBreedsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnlineViewModel(getCatBreedsUseCase: GetCatBreedsUseCase) : ViewModel() {
    private val _state = MutableStateFlow(OnlineState())
    val state: StateFlow<OnlineState> = _state

    init {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            val result = withContext(Dispatchers.IO) {
                getCatBreedsUseCase()
            }
            _state.update { state ->
                state.copy(
                    isLoading = false,
                    breedsModel = result
                )
            }
        }
    }
}
