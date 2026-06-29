package com.arisman.nangdia.domain.model

import com.arisman.nangdia.domain.model.MediaSearchResult

data class MediaDetail(
    val title: String,
    val year: Int? = null,
    val released: String? = null,
    val releasedDigital: String? = null,
    val description: String? = null,
    val runtimeMinutes: Int? = null,
    val score: Int? = null,
    val scoreAverage: Double? = null,
    val mediaType: String = "movie",
    val imdbId: String? = null,
    val tmdbId: Int? = null,
    val traktId: Int? = null,
    val tvdbId: Int? = null,
    val malId: Int? = null,
    val mdblistId: String? = null,
    val tmdbSlug: String? = null,
    val rtSlug: String? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val certification: String? = null,
    val trailer: String? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val popularity: Double? = null,
    val rank: Int? = null,
    val seasonsCount: Int? = null,
    val episodesCount: Int? = null,
    val language: String? = null,
    val spokenLanguage: String? = null,
    val country: String? = null,
    val status: String? = null,
    val voteCount: Int? = null,
    val ratings: List<MediaRating> = emptyList(),
    val genres: List<String> = emptyList(),
    val streams: List<MediaStream> = emptyList(),
    val keywords: List<String> = emptyList(),
    val productionCompanies: List<String> = emptyList(),
    val ageRating: String? = null,
    val awardWins: Int = 0,
    val awardNominations: Int = 0,
    val recommendations: List<MediaSearchResult> = emptyList(),
    val cast: List<MediaCast> = emptyList(),
    val parentalGuideCategories: List<ParentalGuideCategory> = emptyList(),
    val seasons: List<MediaSeason> = emptyList()
)

data class MediaSeason(
    val id: Int,
    val name: String,
    val overview: String?,
    val seasonNumber: Int,
    val airDate: String?,
    val posterPath: String?,
    val episodeCount: Int,
    val episodes: List<MediaEpisode> = emptyList()
)

data class MediaEpisode(
    val id: Int,
    val name: String,
    val overview: String?,
    val airDate: String?,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val stillPath: String?,
    val voteAverage: Double?,
    val runtime: Int?
)

data class ParentalGuideCategory(
    val category: String,
    val severity: String,
    val voteCount: Int,
    val reviews: List<String>
)

data class MediaRating(
    val source: String,
    val value: Double?,
    val score: Double?,
    val votes: Int?,
    val url: String?
)

data class MediaStream(
    val id: Int?,
    val name: String?
)

