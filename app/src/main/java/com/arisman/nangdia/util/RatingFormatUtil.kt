package com.arisman.nangdia.util

import androidx.compose.ui.graphics.Color
import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaRating
import com.arisman.nangdia.ui.theme.RatingHigh
import com.arisman.nangdia.ui.theme.RatingLow
import com.arisman.nangdia.ui.theme.RatingMedium
import com.arisman.nangdia.ui.theme.RatingNeutral
import com.arisman.nangdia.ui.theme.SeverityModerate

object RatingFormatUtil {

    fun getNormalizedScore(rating: MediaRating): Int? {
        val source = normalizeSource(rating.source)
        val scoreOrValue = rating.score ?: rating.value
        val metacriticUserValue = rating.value ?: rating.score?.let { score ->
            when {
                score <= 10.0 -> score
                else -> score / 10.0
            }
        }

        val normalized = when {
            source.contains("metacriticuser") -> metacriticUserValue?.times(10)
            source.contains("imdb") -> normalizeHundredScale(scoreOrValue)
            source.contains("tmdb") -> normalizeHundredScale(scoreOrValue)
            source.contains("trakt") -> normalizeHundredScale(scoreOrValue)
            source.contains("myanimelist") || source == "mal" -> normalizeHundredScale(scoreOrValue)
            source.contains("letterboxd") -> normalizeLetterboxdScale(scoreOrValue)
            source.contains("metacritic") -> normalizeHundredScale(scoreOrValue)
            source.contains("rotten") || source.contains("tomatoes") || source == "rt" || source.contains("popcorn") || source.contains("audience") ->
                normalizeHundredScale(scoreOrValue)
            rating.score != null -> normalizeUnknownScale(rating.score)
            rating.value != null -> normalizeUnknownScale(rating.value)
            else -> null
        }

        return normalized?.toInt()?.coerceIn(0, 100)
    }

    fun getRatingColor(score: Int?): Color {
        if (score == null) return RatingNeutral
        return when {
            score >= 70 -> RatingHigh
            score >= 40 -> RatingMedium
            else -> RatingLow
        }
    }

    fun getSeverityColor(severity: String): Color =
        when (severity.lowercase()) {
            "none" -> RatingHigh
            "mild" -> RatingMedium
            "moderate" -> SeverityModerate
            "severe" -> RatingLow
            else -> RatingNeutral
        }

    fun getFormattedRating(rating: MediaRating): String {
        val source = normalizeSource(rating.source)
        val originalValue = rating.value
        val metacriticUserValue = originalValue ?: rating.score?.let { score ->
            when {
                score <= 10.0 -> score
                else -> score / 10.0
            }
        }
        // Use value (original source scale) when available; fall back to score normalized to source scale
        val displayValue = originalValue ?: rating.score?.let { score ->
            when {
                source.contains("letterboxd") -> if (score <= 5.0) score else if (score <= 10.0) score else score / 20.0
                source.contains("imdb") || source.contains("tmdb") || source.contains("trakt") || source.contains("myanimelist") || source.contains("mal") ->
                    if (score <= 10.0) score else score / 10.0
                else -> score
            }
        }
        return when {
            source.contains("metacriticuser") -> metacriticUserValue?.let { "${trimTrailingZero(it)}/10" } ?: "N/A"
            source.contains("imdb") -> "${displayValue?.let { trimTrailingZero(it) } ?: "N/A"}/10"
            source.contains("tmdb") -> "${displayValue?.let { trimTrailingZero(it) } ?: "N/A"}/10"
            source.contains("letterboxd") -> "${displayValue?.let { trimTrailingZero(it) } ?: "N/A"}/5"
            source.contains("metacritic") -> "${displayValue?.toInt() ?: "N/A"}/100"
            source.contains("trakt") -> "${displayValue?.let { trimTrailingZero(it) } ?: "N/A"}/10"
            source.contains("myanimelist") || source.contains("mal") -> "${displayValue?.let { trimTrailingZero(it) } ?: "N/A"}/10"
            source.contains("rotten") || source.contains("rt") -> "${displayValue?.toInt() ?: "N/A"}%"
            else -> getNormalizedScore(rating)?.let { "$it%" } ?: "N/A"
        }
    }

    fun getSourceUrl(rating: MediaRating, detail: MediaDetail): String? {
        val source = rating.source.lowercase()
        
        // Priority 1: Use direct URL from API if available and valid
        if (!rating.url.isNullOrEmpty() && rating.url != "null" && rating.url.startsWith("http")) {
            return rating.url
        }
        
        val titleQuery = detail.title.replace(" ", "%20")
        return when {
            source.contains("imdb") && !detail.imdbId.isNullOrEmpty() ->
                "https://www.imdb.com/title/${detail.imdbId}/"
            source.contains("tmdb") && detail.tmdbId != null ->
                "https://www.themoviedb.org/${detail.mediaType}/${detail.tmdbId}"
            source.contains("trakt") && !detail.imdbId.isNullOrEmpty() ->
                "https://trakt.tv/search/imdb/${detail.imdbId}"
            source.contains("letterboxd") && !detail.imdbId.isNullOrEmpty() ->
                "https://letterboxd.com/imdb/${detail.imdbId}"
            // NOTE: Must check rogerebert BEFORE rt, because "ebert" ends with "rt"
            source.contains("rogerebert") ->
                "https://www.rogerebert.com/search?q=$titleQuery"
            source.contains("rotten") || source.contains("rt") || source.contains("tomatoes") || source.contains("popcorn") || source.contains("audience") ->
                if (!detail.rtSlug.isNullOrEmpty()) {
                    val mediaBase = if (detail.mediaType == "show" || detail.mediaType == "tv") "tv" else "m"
                    "https://www.rottentomatoes.com/$mediaBase/${detail.rtSlug}"
                } else {
                    "https://www.rottentomatoes.com/search?search=$titleQuery"
                }
            source.contains("metacritic") ->
                "https://www.metacritic.com/search/all/$titleQuery/results"
            source.contains("myanimelist") || source.contains("mal") ->
                "https://myanimelist.net/search/all?q=$titleQuery"
            source.contains("mdblist") && !detail.imdbId.isNullOrEmpty() -> {
                val mediaBase = if (detail.mediaType == "show" || detail.mediaType == "tv") "show" else "movie"
                "https://mdblist.com/$mediaBase/${detail.imdbId}"
            }
            else -> null
        }
    }

    fun shortenSourceName(source: String): String {
        val lowerSource = normalizeSource(source)
        return when {
            lowerSource.contains("metacriticuser") -> "MC User"
            lowerSource.contains("metacritic") -> "MC"
            lowerSource == "popcorn" || lowerSource.contains("tomatoesaudience") || lowerSource.contains("rottentomatoesaudience") -> "Popcorn"
            // NOTE: Must check rogerebert BEFORE rt, because "ebert" ends with "rt"
            lowerSource.contains("rogerebert") -> "Ebert"
            lowerSource.contains("rotten") || lowerSource.contains("tomatoes") || lowerSource.contains("rt") -> "Tomato"
            lowerSource.contains("imdb") -> "IMDb"
            lowerSource.contains("tmdb") || lowerSource.contains("themoviedb") -> "TMDB"
            lowerSource.contains("letterboxd") -> "LBxD"
            lowerSource.contains("trakt") -> "Trakt"
            lowerSource.contains("myanimelist") || lowerSource.contains("mal") -> "MAL"
            lowerSource.contains("mdblist") -> "MdbL"
            else -> source.take(6).uppercase()
        }
    }

    private fun normalizeSource(source: String): String {
        return source.lowercase().replace(" ", "")
    }

    private fun normalizeHundredScale(value: Double?): Double? {
        return when {
            value == null -> null
            value <= 10.0 -> value * 10
            else -> value
        }
    }

    private fun normalizeLetterboxdScale(value: Double?): Double? {
        return when {
            value == null -> null
            value <= 5.0 -> value * 20
            value <= 10.0 -> value * 10
            else -> value
        }
    }

    private fun normalizeUnknownScale(value: Double?): Double? {
        return when {
            value == null -> null
            value <= 5.0 -> value * 20
            value <= 10.0 -> value * 10
            else -> value
        }
    }

    private fun trimTrailingZero(value: Double): String {
        val whole = value.toInt()
        return if (value == whole.toDouble()) {
            whole.toString()
        } else {
            value.toString()
        }
    }
}

