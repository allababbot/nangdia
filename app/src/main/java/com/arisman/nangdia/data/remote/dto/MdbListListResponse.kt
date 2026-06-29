package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MdbListListResponse(
    val movies: List<MdbListItem> = emptyList(),
    val shows: List<MdbListItem> = emptyList()
)

@Serializable
data class MdbListItem(
    val id: Int? = null,
    val title: String,
    val year: Int? = null,
    @SerialName("release_year") val releaseYear: Int? = null,
    val type: String? = null,
    @SerialName("mediatype") val mediaType: String? = null,
    val poster: String? = null,
    val score: Int? = null,
    @SerialName("score_average") val scoreAverage: Double? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    val rating: Double? = null,
    val ratings: List<Rating> = emptyList(),
    val description: String? = null,
    val ids: MdbListIds? = null
)

@Serializable
data class MdbListIds(
    val imdb: String? = null,
    val tmdb: Int? = null,
    val trakt: Int? = null,
    val tvdb: Int? = null
)

