package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeasonResponse(
    val _id: String? = null,
    @SerialName("air_date") val airDate: String? = null,
    val episodes: List<TvEpisodeDto> = emptyList(),
    val name: String? = null,
    val overview: String? = null,
    val id: Int,
    val poster_path: String? = null,
    @SerialName("season_number") val seasonNumber: Int? = null
)

@Serializable
data class TvEpisodeDto(
    @SerialName("air_date") val airDate: String? = null,
    @SerialName("episode_number") val episodeNumber: Int,
    val id: Int,
    val name: String? = null,
    val overview: String? = null,
    @SerialName("production_code") val productionCode: String? = null,
    val runtime: Int? = null,
    @SerialName("season_number") val seasonNumber: Int? = null,
    @SerialName("show_id") val showId: Int? = null,
    @SerialName("still_path") val stillPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null,
    val crew: List<CrewMemberDto> = emptyList(),
    @SerialName("guest_stars") val guestStars: List<CastMemberDto> = emptyList()
)
