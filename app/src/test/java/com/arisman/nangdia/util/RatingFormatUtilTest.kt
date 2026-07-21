package com.arisman.nangdia.util

import com.arisman.nangdia.domain.model.MediaRating
import org.junit.Assert.assertEquals
import org.junit.Test

class RatingFormatUtilTest {

    @Test
    fun `getNormalizedScore converts metacritic user rating score on ten-point scale`() {
        val rating = MediaRating(
            source = "Metacritic User",
            value = null,
            score = 7.8,
            votes = 1200,
            url = null
        )

        assertEquals(78, RatingFormatUtil.getNormalizedScore(rating))
    }

    @Test
    fun `getFormattedRating keeps metacritic user source on ten-point scale`() {
        val rating = MediaRating(
            source = "Metacritic User",
            value = 7.8,
            score = null,
            votes = 1200,
            url = null
        )

        assertEquals("7.8/10", RatingFormatUtil.getFormattedRating(rating))
    }

    @Test
    fun `getFormattedRating keeps metacritic critic score on hundred-point scale`() {
        val rating = MediaRating(
            source = "Metacritic",
            value = 82.0,
            score = null,
            votes = 200,
            url = null
        )

        assertEquals("82/100", RatingFormatUtil.getFormattedRating(rating))
    }

    @Test
    fun `getNormalizedScore prefers metacritic user value when score is already normalized`() {
        val rating = MediaRating(
            source = "Metacritic User",
            value = 7.4,
            score = 100.0,
            votes = 800,
            url = null
        )

        assertEquals(74, RatingFormatUtil.getNormalizedScore(rating))
    }

    @Test
    fun `getFormattedRating prefers metacritic user value when score is already normalized`() {
        val rating = MediaRating(
            source = "Metacritic User",
            value = 7.4,
            score = 100.0,
            votes = 800,
            url = null
        )

        assertEquals("7.4/10", RatingFormatUtil.getFormattedRating(rating))
    }

    @Test
    fun `shortenSourceName returns Ebert for Roger Ebert not Tomato`() {
        assertEquals("Ebert", RatingFormatUtil.shortenSourceName("Roger Ebert"))
        assertEquals("Ebert", RatingFormatUtil.shortenSourceName("RogerEbert"))
        assertEquals("Ebert", RatingFormatUtil.shortenSourceName("rogerebert"))
    }

    @Test
    fun `shortenSourceName returns Tomato for Rotten Tomatoes`() {
        assertEquals("Tomato", RatingFormatUtil.shortenSourceName("Rotten Tomatoes"))
        assertEquals("Tomato", RatingFormatUtil.shortenSourceName("RottenTomatoes"))
        assertEquals("Tomato", RatingFormatUtil.shortenSourceName("RT"))
    }
}
