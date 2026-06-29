package com.arisman.nangdia.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class AdvancedSearchState(
    val results: List<MediaSearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    
    // Filters
    val mediaType: String = "movie", // "movie" or "tv"
    val sortBy: String = "popularity.desc",
    val selectedGenres: List<Int> = emptyList(),
    val yearStart: Int? = null,
    val yearEnd: Int? = null,
    val minRating: Float = 0f,
    val language: String = "en"
)

@HiltViewModel
class AdvancedSearchViewModel @Inject constructor(
    private val repository: MdbListRepository
) : ViewModel() {

    private val _state = mutableStateOf(AdvancedSearchState())
    val state: State<AdvancedSearchState> = _state

    fun onMediaTypeChange(type: String) {
        _state.value = _state.value.copy(mediaType = type, selectedGenres = emptyList())
    }

    fun onSortByChange(sort: String) {
        _state.value = _state.value.copy(sortBy = sort)
    }

    fun onGenreToggle(genreId: Int) {
        val currentGenres = _state.value.selectedGenres
        val newGenres = if (currentGenres.contains(genreId)) {
            currentGenres - genreId
        } else {
            currentGenres + genreId
        }
        _state.value = _state.value.copy(selectedGenres = newGenres)
    }

    fun onYearStartChange(year: Int?) {
        _state.value = _state.value.copy(yearStart = year)
    }

    fun onYearEndChange(year: Int?) {
        _state.value = _state.value.copy(yearEnd = year)
    }

    fun onRatingChange(rating: Float) {
        _state.value = _state.value.copy(minRating = rating)
    }

    fun onLanguageChange(lang: String) {
        _state.value = _state.value.copy(language = lang)
    }

    fun search() {
        val genresString = if (_state.value.selectedGenres.isNotEmpty()) {
            _state.value.selectedGenres.joinToString(",")
        } else null

        repository.discoverMedia(
            type = _state.value.mediaType,
            sortBy = _state.value.sortBy,
            genres = genresString,
            yearStart = _state.value.yearStart,
            yearEnd = _state.value.yearEnd,
            voteAverageGte = _state.value.minRating,
            language = _state.value.language
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        results = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    
    fun resetFilters() {
        _state.value = AdvancedSearchState()
    }
}
