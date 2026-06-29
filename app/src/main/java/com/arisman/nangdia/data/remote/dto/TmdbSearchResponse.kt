package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbSearchResponse(
    val results: List<TmdbSearchResultDto>
)

@Serializable
data class TmdbSearchResultDto(
    val id: Int,
    val title: String? = null,
    val name: String? = null, // TV shows use 'name' instead of 'title'
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null
)

