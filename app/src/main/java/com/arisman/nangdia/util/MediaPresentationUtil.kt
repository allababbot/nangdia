package com.arisman.nangdia.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object MediaPresentationUtil {

    private val indonesianLocale = Locale("id", "ID")
    private val longDateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", indonesianLocale)

    fun toDisplayMediaType(mediaType: String?): String {
        return when (mediaType?.lowercase()) {
            "tv", "show" -> "TV Series"
            else -> "Movie"
        }
    }

    fun formatLongIndonesianDate(rawDate: String): String {
        return try {
            LocalDate.parse(rawDate).format(longDateFormatter)
        } catch (_: Exception) {
            rawDate
        }
    }
}
