package com.arisman.nangdia.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WatchlistState())
    val state: StateFlow<WatchlistState> = _state.asStateFlow()

    init {
        getWatchlist()
    }

    private fun getWatchlist() {
        _state.update { it.copy(isLoading = true) }
        repository.getWatchlist()
            .onEach { items ->
                _state.update { it.copy(
                    items = items,
                    isLoading = false
                ) }
            }
            .launchIn(viewModelScope)
    }

    fun removeFromWatchlist(id: String) {
        viewModelScope.launch {
            repository.removeFromWatchlist(id)
        }
    }
}

