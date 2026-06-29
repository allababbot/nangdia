package com.arisman.nangdia.util

import org.junit.Assert.assertEquals
import org.junit.Test

class MediaPresentationUtilTest {

    @Test
    fun `toDisplayMediaType maps show to tv series`() {
        assertEquals("TV Series", MediaPresentationUtil.toDisplayMediaType("show"))
    }

    @Test
    fun `toDisplayMediaType maps tv to tv series`() {
        assertEquals("TV Series", MediaPresentationUtil.toDisplayMediaType("tv"))
    }

    @Test
    fun `toDisplayMediaType maps movie to movie`() {
        assertEquals("Movie", MediaPresentationUtil.toDisplayMediaType("movie"))
    }

    @Test
    fun `formatLongIndonesianDate formats iso date with indonesian month`() {
        assertEquals("15 Maret 2026", MediaPresentationUtil.formatLongIndonesianDate("2026-03-15"))
    }

    @Test
    fun `formatLongIndonesianDate falls back to raw value when parsing fails`() {
        assertEquals("unknown", MediaPresentationUtil.formatLongIndonesianDate("unknown"))
    }
}
