package com.arisman.nangdia.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MdbListRepository
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _state.value = _state.value.copy(query = newQuery)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if (newQuery.isNotBlank()) {
                searchMedia(newQuery, _state.value.selectedType, _state.value.selectedYear)
            }
        }
    }

    fun onTypeChange(type: String?) {
        _state.value = _state.value.copy(selectedType = type)
        if (_state.value.query.isNotBlank()) {
            searchMedia(_state.value.query, type, _state.value.selectedYear)
        }
    }

    fun onYearChange(year: String?) {
        _state.value = _state.value.copy(selectedYear = year)
        if (_state.value.query.isNotBlank()) {
            searchMedia(_state.value.query, _state.value.selectedType, year)
        }
    }

    fun toggleFilters() {
        _state.value = _state.value.copy(isFiltersVisible = !_state.value.isFiltersVisible)
    }

    private fun searchMedia(query: String, type: String? = null, year: String? = null) {
        repository.searchMedia(query, type, year).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newResults = result.data ?: emptyList()
                    _state.value = _state.value.copy(
                        results = newResults,
                        isLoading = false,
                        error = null
                    )
                    fetchCastForResults(newResults)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        results = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchCastForResults(medias: List<MediaSearchResult>) {
        medias.forEach { media ->
            viewModelScope.launch {
                repository.getMediaDetail(media.id.toString(), media.mediaType, "tmdb")
                    .onEach { result ->
                        if (result is Resource.Success) {
                            val castNames = result.data?.cast?.take(5)?.map { it.name } ?: emptyList()
                            val currentMap = _state.value.castMap.toMutableMap()
                            currentMap[media.id.toString()] = castNames
                            _state.value = _state.value.copy(castMap = currentMap)
                        }
                    }
                    .launchIn(viewModelScope)
            }
        }
    }
}
