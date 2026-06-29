package com.arisman.nangdia.domain.model

data class MediaSearchResult(
    val id: Int,
    val title: String,
    val year: Int?,
    val mediaType: String,
    val imdbId: String?,
    val tmdbId: Int?,
    val traktId: Int?,
    val score: Int?,
    val poster: String?,
    val cast: List<String> = emptyList()
)

