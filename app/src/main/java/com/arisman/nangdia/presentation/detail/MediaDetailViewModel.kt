package com.arisman.nangdia.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.model.WatchlistMedia
import com.arisman.nangdia.domain.repository.WatchlistRepository
import com.arisman.nangdia.domain.use_case.GetMediaDetailUseCase
import com.arisman.nangdia.domain.use_case.GetTvSeasonDetailUseCase
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val getMediaDetailUseCase: GetMediaDetailUseCase,
    private val getTvSeasonDetailUseCase: GetTvSeasonDetailUseCase,
    private val watchlistRepository: WatchlistRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MediaDetailState())
    val state: State<MediaDetailState> = _state

    private val _isBookmarked = mutableStateOf(false)
    val isBookmarked: State<Boolean> = _isBookmarked

    private var currentMediaId: String? = null
    private var currentMediaType: String? = null
    private var currentProvider: String? = null

    init {
        currentMediaId = savedStateHandle.get<String>("mediaId")
        currentMediaType = savedStateHandle.get<String>("mediaType") ?: "movie"
        currentProvider = savedStateHandle.get<String>("provider") ?: "imdb"
        
        if (currentMediaId != null) {
            _state.value = _state.value.copy(
                mediaId = currentMediaId,
                provider = currentProvider
            )
            getMediaDetail(currentMediaId!!, currentMediaType!!, currentProvider!!)
            checkIfBookmarked()
        }
    }

    private fun checkIfBookmarked() {
        val id = "${currentProvider}_${currentMediaId}"
        viewModelScope.launch {
            _isBookmarked.value = watchlistRepository.getWatchlistById(id) != null
        }
    }

    fun toggleWatchlist() {
        viewModelScope.launch {
            val id = "${currentProvider}_${currentMediaId}"
            if (_isBookmarked.value) {
                watchlistRepository.removeFromWatchlist(id)
                _isBookmarked.value = false
            } else {
                state.value.mediaDetail?.let { detail ->
                    watchlistRepository.addToWatchlist(
                        WatchlistMedia(
                            id = id,
                            mediaId = currentMediaId ?: "",
                            title = detail.title,
                            poster = detail.poster,
                            mediaType = detail.mediaType,
                            provider = currentProvider ?: "imdb",
                            year = detail.year,
                            score = detail.score,
                            released = detail.released,
                            releasedDigital = detail.releasedDigital
                        )
                    )
                    _isBookmarked.value = true
                }
            }
        }
    }

    private fun getMediaDetail(id: String, mediaType: String, provider: String) {
        getMediaDetailUseCase(id, mediaType, provider).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        mediaDetail = result.data,
                        isLoading = false
                    )
                    
                    // If it's a TV show, fetch the first season details
                    if (mediaType == "show" || mediaType == "tv") {
                        val tmdbId = result.data?.tmdbId
                        if (tmdbId != null) {
                            val firstSeasonNumber = preferredInitialSeasonNumber(result.data?.seasons.orEmpty())
                            if (firstSeasonNumber != null) {
                                getTvSeasonDetail(tmdbId, firstSeasonNumber)
                            }
                        }
                    }
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

    private fun getTvSeasonDetail(tvId: Int, seasonNumber: Int) {
        getTvSeasonDetailUseCase(tvId, seasonNumber).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        episodes = result.data?.episodes ?: emptyList(),
                        selectedSeasonNumber = seasonNumber,
                        isEpisodesLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isEpisodesLoading = false,
                        error = result.message ?: "Failed to load episodes"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isEpisodesLoading = true,
                        selectedSeasonNumber = seasonNumber,
                        error = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSeasonChange(seasonNumber: Int) {
        val tmdbId = _state.value.mediaDetail?.tmdbId
        if (tmdbId != null) {
            getTvSeasonDetail(tmdbId, seasonNumber)
        }
    }
}

