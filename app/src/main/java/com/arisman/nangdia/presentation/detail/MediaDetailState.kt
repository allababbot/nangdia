package com.arisman.nangdia.presentation.detail

import com.arisman.nangdia.domain.model.MediaDetail

data class MediaDetailState(
    val mediaDetail: MediaDetail? = null,
    val mediaId: String? = null,
    val provider: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedSeasonNumber: Int = 1,
    val episodes: List<com.arisman.nangdia.domain.model.MediaEpisode> = emptyList(),
    val isEpisodesLoading: Boolean = false
)

