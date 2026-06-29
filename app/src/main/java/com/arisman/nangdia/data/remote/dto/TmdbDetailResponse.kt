package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbDetailResponse(
    val id: Int? = null,
    val title: String? = null,
    val name: String? = null,
    val overview: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val status: String? = null,
    val popularity: Double? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    val genres: List<TmdbGenreDto> = emptyList(),
    @SerialName("release_dates") val releaseDates: TmdbReleaseDates? = null,
    @SerialName("content_ratings") val contentRatings: TmdbContentRatings? = null,
    val recommendations: TmdbSearchResponse? = null,
    val credits: TmdbCreditsResponse? = null,
    val seasons: List<TmdbSeasonDto> = emptyList()
)

@Serializable
data class TmdbGenreDto(
    val id: Int? = null,
    val name: String? = null
)

@Serializable
data class TmdbSeasonDto(
    val id: Int? = null,
    val name: String? = null,
    val overview: String? = null,
    @SerialName("season_number") val seasonNumber: Int? = null,
    @SerialName("air_date") val airDate: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("episode_count") val episodeCount: Int? = null
)

@Serializable
data class TmdbReleaseDates(
    val results: List<TmdbReleaseDateResult> = emptyList()
)

@Serializable
data class TmdbReleaseDateResult(
    @SerialName("iso_3166_1") val iso: String? = null,
    @SerialName("release_dates") val releaseDates: List<TmdbReleaseDateItem> = emptyList()
)

@Serializable
data class TmdbReleaseDateItem(
    val certification: String? = null
)

@Serializable
data class TmdbContentRatings(
    val results: List<TmdbContentRatingResult> = emptyList()
)

@Serializable
data class TmdbContentRatingResult(
    @SerialName("iso_3166_1") val iso: String? = null,
    val rating: String? = null
)

