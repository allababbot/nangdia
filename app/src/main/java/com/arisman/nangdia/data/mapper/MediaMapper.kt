package com.arisman.nangdia.data.mapper

import com.arisman.nangdia.data.remote.dto.GenreDto
import com.arisman.nangdia.data.remote.dto.MdbListItem
import com.arisman.nangdia.data.remote.dto.MediaDetailResponse
import com.arisman.nangdia.data.remote.dto.Rating
import com.arisman.nangdia.data.remote.dto.SearchItem
import com.arisman.nangdia.data.remote.dto.Stream
import com.arisman.nangdia.data.remote.dto.TmdbSearchResultDto
import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaRating
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.domain.model.MediaStream
import kotlinx.serialization.json.*

fun SearchItem.toMediaSearchResult(): MediaSearchResult {
    val formattedPoster = formatPosterUrl(poster ?: posterPath ?: image ?: thumbnail ?: backdrop ?: backdropPath)

    return MediaSearchResult(
        id = ids?.tmdbId ?: ids?.traktId ?: 0,
        title = title,
        year = year,
        mediaType = type,
        imdbId = ids?.imdbId,
        tmdbId = ids?.tmdbId,
        traktId = ids?.traktId,
        score = score,
        poster = formattedPoster,
        cast = credits?.cast?.take(2)?.map { it.name } ?: emptyList()
    )
}

fun MdbListItem.toMediaSearchResult(): MediaSearchResult {
    val formattedPoster = formatPosterUrl(poster)
    val mdblistScore = ratings.firstOrNull { it.source.equals("mdblist", ignoreCase = true) }?.score?.toInt()

    return MediaSearchResult(
        id = ids?.tmdb ?: ids?.trakt ?: id ?: 0,
        title = title,
        year = year ?: releaseYear,
        mediaType = when (type ?: mediaType) {
            "tv" -> "show"
            else -> type ?: mediaType ?: "movie"
        },
        imdbId = ids?.imdb ?: imdbId,
        tmdbId = ids?.tmdb,
        traktId = ids?.trakt,
        score = score ?: scoreAverage?.toInt() ?: mdblistScore ?: (rating?.times(10))?.toInt(),
        poster = formattedPoster
    )
}

fun TmdbSearchResultDto.toMediaSearchResult(fallbackMediaType: String? = null): MediaSearchResult {
    val formattedPoster = formatPosterUrl(posterPath)
    
    // Extract year from release_date or first_air_date
    val yearString = releaseDate?.take(4) ?: firstAirDate?.take(4)
    val yearInt = yearString?.toIntOrNull()

    val mappedMediaType = when (mediaType ?: fallbackMediaType) {
        "tv" -> "show"
        "show" -> "show"
        else -> fallbackMediaType ?: mediaType ?: "movie"
    }

    return MediaSearchResult(
        id = id,
        title = title ?: name ?: "Unknown",
        year = yearInt,
        mediaType = mappedMediaType,
        imdbId = null, // Will be fetched later if needed
        tmdbId = id,
        traktId = null,
        score = (voteAverage?.times(10))?.toInt(), // Convert 0-10 to 0-100
        poster = formattedPoster
    )
}

fun MediaDetailResponse.toMediaDetail(): MediaDetail {
    val formattedPoster = formatPosterUrl(poster ?: posterPath ?: image ?: backdrop ?: backdropPath)
    
    val backdropUrl = backdrop ?: backdropPath ?: image
    val formattedBackdrop = when {
        backdropUrl.isNullOrEmpty() -> null
        backdropUrl.startsWith("http") -> backdropUrl
        backdropUrl.startsWith("//") -> "https:$backdropUrl"
        backdropUrl.startsWith("/") -> "https://image.tmdb.org/t/p/original$backdropUrl"
        else -> "https://image.tmdb.org/t/p/original/$backdropUrl"
    }

    return MediaDetail(
        title = title,
        year = year,
        released = released,
        releasedDigital = releasedDigital,
        description = description,
        runtimeMinutes = runtime,
        score = score,
        scoreAverage = scoreAverage,
        mediaType = type,
        imdbId = ids?.imdb,
        tmdbId = ids?.tmdb,
        traktId = ids?.trakt,
        tvdbId = ids?.tvdb,
        malId = ids?.mal,
        mdblistId = ids?.mdblistId,
        tmdbSlug = ids?.tmdbSlug,
        rtSlug = ids?.rtSlug,
        poster = formattedPoster,
        backdrop = formattedBackdrop,
        certification = certification,
        trailer = trailer,
        budget = budget,
        revenue = revenue,
        popularity = popularity,
        rank = rank,
        seasonsCount = seasonsCount,
        episodesCount = episodesCount,
        ratings = ratings.map { it.toMediaRating() },
        genres = genres.mapNotNull { it.name },
        streams = streams.map { it.toMediaStream() },
        keywords = keywords.mapNotNull { 
            try { it.jsonObject["name"]?.jsonPrimitive?.content } 
            catch (e: Exception) { it.toString().removeSurrounding("\"") } 
        },
        productionCompanies = productionCompanies.mapNotNull { 
            try { it.jsonObject["name"]?.jsonPrimitive?.content } 
            catch (e: Exception) { it.toString().removeSurrounding("\"") } 
        },
        ageRating = ageRating?.toString()?.removeSurrounding("\""),
        language = language,
        spokenLanguage = spokenLanguage,
        country = country,
        status = status,
        awardWins = 0,
        awardNominations = 0,
        recommendations = emptyList(),
        cast = emptyList() // Will be populated from TMDB
    )
}

fun com.arisman.nangdia.data.remote.dto.CastMemberDto.toMediaCast(): com.arisman.nangdia.domain.model.MediaCast {
    return com.arisman.nangdia.domain.model.MediaCast(
        id = id,
        name = name,
        character = character,
        profilePath = if (profilePath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w185$profilePath"
    )
}

fun com.arisman.nangdia.data.remote.dto.TmdbPersonDetailResponse.toPersonDetail(
    credits: List<com.arisman.nangdia.domain.model.PersonCastCredit>
): com.arisman.nangdia.domain.model.PersonDetail {
    return com.arisman.nangdia.domain.model.PersonDetail(
        id = id,
        name = name,
        biography = biography,
        birthday = birthday,
        placeOfBirth = placeOfBirth,
        profilePath = if (profilePath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/h632$profilePath",
        knownFor = knownFor,
        popularity = popularity,
        combinedCredits = credits
    )
}

fun com.arisman.nangdia.data.remote.dto.PersonCastCreditDto.toPersonCastCredit(): com.arisman.nangdia.domain.model.PersonCastCredit {
    return com.arisman.nangdia.domain.model.PersonCastCredit(
        id = id,
        title = title ?: name ?: "Unknown",
        mediaType = mediaType ?: "movie",
        character = character,
        posterPath = if (posterPath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w342$posterPath",
        rating = voteAverage,
        year = releaseDate?.take(4) ?: firstAirDate?.take(4)
    )
}

private fun formatPosterUrl(poster: String?): String? {
    if (poster.isNullOrBlank() || poster.lowercase() == "n/a") return null
    
    // If it's already a full URL, return it
    if (poster.startsWith("http", ignoreCase = true)) return poster
    if (poster.startsWith("//")) return "https:$poster"

    // Handle TMDB relative paths (they usually start with /)
    val path = if (poster.startsWith("/")) poster else "/$poster"

    // Basic validation for TMDB style paths (e.g., /abc123.jpg)
    if (path.length < 5 || !path.contains(".")) return null

    return "https://image.tmdb.org/t/p/w500$path"
}

fun Rating.toMediaRating(): MediaRating {
    return MediaRating(
        source = source,
        value = value,
        score = score,
        votes = votes,
        url = url?.toString()?.removeSurrounding("\"")
    )
}

fun Stream.toMediaStream(): MediaStream {
    return MediaStream(
        id = id,
        name = name
    )
}

fun com.arisman.nangdia.data.remote.dto.TvSeasonResponse.toMediaSeason(): com.arisman.nangdia.domain.model.MediaSeason {
    return com.arisman.nangdia.domain.model.MediaSeason(
        id = id,
        name = name ?: "Unknown",
        overview = overview,
        seasonNumber = seasonNumber ?: 0,
        airDate = airDate,
        posterPath = if (poster_path.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w342$poster_path",
        episodeCount = episodes.size,
        episodes = episodes.map { it.toMediaEpisode() }
    )
}

fun com.arisman.nangdia.data.remote.dto.TmdbSeasonDto.toMediaSeason(): com.arisman.nangdia.domain.model.MediaSeason {
    return com.arisman.nangdia.domain.model.MediaSeason(
        id = id ?: 0,
        name = name ?: "Unknown",
        overview = overview,
        seasonNumber = seasonNumber ?: 0,
        airDate = airDate,
        posterPath = if (posterPath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w342$posterPath",
        episodeCount = episodeCount ?: 0,
        episodes = emptyList() // Episodes will be fetched separately
    )
}

fun com.arisman.nangdia.data.remote.dto.TvEpisodeDto.toMediaEpisode(): com.arisman.nangdia.domain.model.MediaEpisode {
    return com.arisman.nangdia.domain.model.MediaEpisode(
        id = id,
        name = name ?: "Unknown",
        overview = overview,
        airDate = airDate,
        episodeNumber = episodeNumber,
        seasonNumber = seasonNumber ?: 0,
        stillPath = if (stillPath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w300$stillPath",
        voteAverage = voteAverage,
        runtime = runtime
    )
}
