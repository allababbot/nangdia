package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbPersonDetailResponse(
    val id: Int,
    val name: String,
    val biography: String? = null,
    val birthday: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("also_known_as") val alsoKnownAs: List<String> = emptyList(),
    val gender: Int? = null,
    val popularity: Double? = null,
    @SerialName("known_for_department") val knownFor: String? = null
)

@Serializable
data class TmdbPersonCreditsResponse(
    val cast: List<PersonCastCreditDto> = emptyList(),
    val crew: List<PersonCrewCreditDto> = emptyList()
)

@Serializable
data class PersonCastCreditDto(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    val character: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null
)

@Serializable
data class PersonCrewCreditDto(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    val job: String? = null,
    val department: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null
)
