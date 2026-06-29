package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val search: List<SearchItem> = emptyList(),
    val total: Int = 0,
    val page: Int = 1
)

@Serializable
data class SearchItem(
    val title: String,
    val year: Int? = null,
    val type: String,
    val score: Int? = null,
    val poster: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("thumbnail") val thumbnail: String? = null,
    val backdrop: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val credits: com.arisman.nangdia.data.remote.dto.TmdbCreditsResponse? = null,
    val ids: SearchIds? = null
)

@Serializable
data class SearchIds(
    @SerialName("imdbid") val imdbId: String? = null,
    @SerialName("tmdbid") val tmdbId: Int? = null,
    @SerialName("traktid") val traktId: Int? = null
)

