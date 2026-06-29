package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbCreditsResponse(
    val cast: List<CastMemberDto> = emptyList(),
    val crew: List<CrewMemberDto> = emptyList()
)

@Serializable
data class CastMemberDto(
    val id: Int,
    val name: String,
    @SerialName("original_name") val originalName: String? = null,
    val character: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    val order: Int? = null,
    val gender: Int? = null,
    val popularity: Double? = null
)

@Serializable
data class CrewMemberDto(
    val id: Int,
    val name: String,
    val job: String? = null,
    val department: String? = null,
    @SerialName("profile_path") val profilePath: String? = null
)
