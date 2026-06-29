package com.arisman.nangdia.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaEpisode
import com.arisman.nangdia.domain.use_case.GetMediaDetailUseCase
import com.arisman.nangdia.domain.use_case.GetTvSeasonDetailUseCase
import com.arisman.nangdia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import android.util.Log
import javax.inject.Inject

data class MediaSeasonsState(
    val mediaDetail: MediaDetail? = null,
    val episodes: List<MediaEpisode> = emptyList(),
    val selectedSeasonNumber: Int = 1,
    val isLoading: Boolean = false,
    val isEpisodesLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class MediaSeasonsViewModel @Inject constructor(
    private val getMediaDetailUseCase: GetMediaDetailUseCase,
    private val getTvSeasonDetailUseCase: GetTvSeasonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MediaSeasonsState())
    val state: State<MediaSeasonsState> = _state

    private var currentMediaId: String? = null
    private var currentMediaType: String? = null
    private var currentProvider: String? = null

    init {
        currentMediaId = savedStateHandle.get<String>("mediaId")
        currentMediaType = savedStateHandle.get<String>("mediaType")
        currentProvider = savedStateHandle.get<String>("provider")

        Log.d("MediaSeasonsVM", "Init called with mediaId: $currentMediaId, mediaType: $currentMediaType, provider: $currentProvider")

        val id = currentMediaId
        val type = currentMediaType
        val provider = currentProvider

        if (id != null && type != null && provider != null) {
            getMediaDetail(id, type, provider)
        } else {
            Log.e("MediaSeasonsVM", "Required parameters missing from SavedStateHandle")
            _state.value = _state.value.copy(error = "Required parameters missing")
        }
    }

    private fun getMediaDetail(id: String, mediaType: String, provider: String) {
        Log.d("MediaSeasonsVM", "getMediaDetail called for $id ($mediaType) using $provider")
        android.util.Log.d("MediaSeasonsViewModel", "Fetching detail for ID: $id, Type: $mediaType, Provider: $provider")
        getMediaDetailUseCase(id, mediaType, provider).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val detail = result.data
                    Log.d("MediaSeasonsVM", "getMediaDetail SUCCESS. Title: ${detail?.title}, TMDB ID: ${detail?.tmdbId}")
                    _state.value = _state.value.copy(
                        mediaDetail = detail,
                        isLoading = false
                    )
                    
                    val tmdbId = detail?.tmdbId
                    if (tmdbId != null) {
                        val firstSeasonNumber = preferredInitialSeasonNumber(detail?.seasons.orEmpty())
                        if (firstSeasonNumber != null) {
                            Log.d("MediaSeasonsVM", "Fetching episodes for TMDB ID: $tmdbId, Season: $firstSeasonNumber")
                            getTvSeasonDetail(tmdbId, firstSeasonNumber)
                        } else {
                            Log.w("MediaSeasonsVM", "No valid initial season number recorded. Season fetch aborted.")
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isEpisodesLoading = false,
                                error = "Data musim belum tersedia untuk tayangan ini."
                            )
                        }
                    } else {
                        Log.w("MediaSeasonsVM", "TMDB ID is NULL. Cannot fetch episodes.")
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

    fun getTvSeasonDetail(tvId: Int, seasonNumber: Int) {
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
