package com.arisman.nangdia.data.mapper

import com.arisman.nangdia.data.remote.dto.TmdbSearchResultDto
import com.arisman.nangdia.data.remote.dto.MdbListItem
import com.arisman.nangdia.data.remote.dto.Rating
import org.junit.Assert.assertEquals
import org.junit.Test

class MediaMapperTest {

    @Test
    fun `tmdb discover tv result uses fallback media type when media type field is missing`() {
        val dto = TmdbSearchResultDto(
            id = 10,
            name = "The Bear",
            mediaType = null,
            firstAirDate = "2023-01-01",
            voteAverage = 8.1
        )

        val result = dto.toMediaSearchResult(fallbackMediaType = "tv")

        assertEquals("show", result.mediaType)
    }

    @Test
    fun `tmdb discover movie result keeps movie fallback when media type field is missing`() {
        val dto = TmdbSearchResultDto(
            id = 11,
            title = "Dune",
            mediaType = null,
            releaseDate = "2024-01-01",
            voteAverage = 8.0
        )

        val result = dto.toMediaSearchResult(fallbackMediaType = "movie")

        assertEquals("movie", result.mediaType)
    }

    @Test
    fun `mdblist item falls back to score average when explicit score is missing`() {
        val item = MdbListItem(
            id = 578,
            title = "Jaws",
            year = 1975,
            type = "movie",
            score = null,
            scoreAverage = 86.0,
            rating = null,
            ratings = emptyList()
        )

        val result = item.toMediaSearchResult()

        assertEquals(86, result.score)
    }

    @Test
    fun `mdblist item falls back to mdblist rating when score fields are missing`() {
        val item = MdbListItem(
            id = 100,
            title = "Sample",
            year = 2024,
            type = "movie",
            score = null,
            scoreAverage = null,
            rating = null,
            ratings = listOf(
                Rating(source = "imdb", value = 7.0, score = 70.0, votes = null, url = null),
                Rating(source = "mdblist", value = null, score = 68.0, votes = null, url = null)
            )
        )

        val result = item.toMediaSearchResult()

        assertEquals(68, result.score)
    }
}
