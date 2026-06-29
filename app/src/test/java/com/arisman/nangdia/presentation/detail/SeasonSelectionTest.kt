package com.arisman.nangdia.presentation.detail

import com.arisman.nangdia.domain.model.MediaSeason
import org.junit.Assert.assertEquals
import org.junit.Test

class SeasonSelectionTest {

    @Test
    fun `canonicalMediaType converts tv to show`() {
        assertEquals("show", canonicalMediaType("tv"))
    }

    @Test
    fun `canonicalMediaType keeps existing show value`() {
        assertEquals("show", canonicalMediaType("show"))
    }

    @Test
    fun `canonicalMediaType leaves movie untouched`() {
        assertEquals("movie", canonicalMediaType("movie"))
    }

    @Test
    fun `preferredInitialSeasonNumber skips specials and picks first regular season with episodes`() {
        val seasons = listOf(
            mediaSeason(seasonNumber = 0, episodeCount = 2),
            mediaSeason(seasonNumber = 1, episodeCount = 8),
            mediaSeason(seasonNumber = 2, episodeCount = 10)
        )

        assertEquals(1, preferredInitialSeasonNumber(seasons))
    }

    @Test
    fun `preferredInitialSeasonNumber skips empty placeholders`() {
        val seasons = listOf(
            mediaSeason(seasonNumber = 1, episodeCount = 0),
            mediaSeason(seasonNumber = 2, episodeCount = 12)
        )

        assertEquals(2, preferredInitialSeasonNumber(seasons))
    }

    @Test
    fun `preferredInitialSeasonNumber falls back to first positive season when counts are missing`() {
        val seasons = listOf(
            mediaSeason(seasonNumber = 0, episodeCount = 0),
            mediaSeason(seasonNumber = 3, episodeCount = 0)
        )

        assertEquals(3, preferredInitialSeasonNumber(seasons))
    }

    @Test
    fun `preferredInitialSeasonNumber defaults to season one when nothing usable exists`() {
        assertEquals(1, preferredInitialSeasonNumber(emptyList()))
    }

    private fun mediaSeason(seasonNumber: Int, episodeCount: Int): MediaSeason {
        return MediaSeason(
            id = seasonNumber,
            name = "Season $seasonNumber",
            overview = null,
            seasonNumber = seasonNumber,
            airDate = null,
            posterPath = null,
            episodeCount = episodeCount
        )
    }
}
