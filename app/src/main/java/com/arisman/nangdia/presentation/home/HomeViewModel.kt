package com.arisman.nangdia.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MdbListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getPopularMovies()
        getPopularTvShows()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { it.copy(popularMovies = result.data ?: emptyList()) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(error = result.message) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun getPopularTvShows() {
        viewModelScope.launch {
            repository.getPopularTvShows().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update { 
                            it.copy(
                                popularTvShows = result.data ?: emptyList(),
                                isLoading = false
                            ) 
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}

