package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Serializable
data class MediaDetailResponse(
    val title: String,
    val year: Int? = null,
    val released: String? = null,
    @SerialName("released_digital") val releasedDigital: String? = null,
    val description: String? = null,
    val runtime: Int? = null,
    val score: Int? = null,
    @SerialName("score_average") val scoreAverage: Double? = null,
    val type: String,
    val ids: MediaIds? = null,
    val ratings: List<Rating> = emptyList(),
    val poster: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("image") val image: String? = null,
    val backdrop: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val certification: String? = null,
    val trailer: String? = null,
    val language: String? = null,
    @SerialName("spoken_language") val spokenLanguage: String? = null,
    val country: String? = null,
    val status: String? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val popularity: Double? = null,
    val rank: Int? = null,
    @SerialName("seasons_count") val seasonsCount: Int? = null,
    @SerialName("episodes_count") val episodesCount: Int? = null,
    val genres: List<GenreDto> = emptyList(),
    val streams: List<Stream> = emptyList(),
    val keywords: List<kotlinx.serialization.json.JsonElement> = emptyList(),
    @SerialName("production_companies") val productionCompanies: List<kotlinx.serialization.json.JsonElement> = emptyList(),
    @SerialName("age_rating") val ageRating: kotlinx.serialization.json.JsonElement? = null
)

@Serializable
data class GenreDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class Stream(
    val id: Int? = null,
    val name: String? = null
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class MediaIds(
    val imdb: String? = null,
    val tmdb: Int? = null,
    val trakt: Int? = null,
    val tvdb: Int? = null,
    val mal: Int? = null,
    @SerialName("mdblist") val mdblistId: String? = null,
    @SerialName("tmdb_slug") val tmdbSlug: String? = null,
    @SerialName("rt_slug") val rtSlug: String? = null
)

@Serializable
data class Rating(
    val source: String,
    val value: Double? = null,
    val score: Double? = null,
    val votes: Int? = null,
    val url: kotlinx.serialization.json.JsonElement? = null
)

