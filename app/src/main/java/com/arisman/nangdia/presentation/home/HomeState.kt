package com.arisman.nangdia.presentation.home

import com.arisman.nangdia.domain.model.MediaSearchResult

data class HomeState(
    val popularMovies: List<MediaSearchResult> = emptyList(),
    val popularTvShows: List<MediaSearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

