package com.arisman.nangdia.domain.model

data class MediaCast(
    val id: Int,
    val name: String,
    val character: String?,
    val profilePath: String?
)

data class PersonDetail(
    val id: Int,
    val name: String,
    val biography: String?,
    val birthday: String?,
    val placeOfBirth: String?,
    val profilePath: String?,
    val knownFor: String?,
    val popularity: Double?,
    val combinedCredits: List<PersonCastCredit> = emptyList()
)

data class PersonCastCredit(
    val id: Int,
    val title: String,
    val mediaType: String,
    val character: String?,
    val posterPath: String?,
    val rating: Double?,
    val year: String?
)
