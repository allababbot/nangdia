package com.arisman.nangdia.presentation.detail

import com.arisman.nangdia.domain.model.MediaSeason

internal fun canonicalMediaType(mediaType: String): String {
    return if (mediaType.equals("tv", ignoreCase = true)) "show" else mediaType
}

internal fun preferredInitialSeasonNumber(seasons: List<MediaSeason>): Int? {
    if (seasons.isEmpty()) return 1
    return seasons
        .firstOrNull { it.seasonNumber > 0 && it.episodeCount > 0 }
        ?.seasonNumber
        ?: seasons.firstOrNull { it.seasonNumber > 0 }?.seasonNumber
        ?: seasons.firstOrNull()?.seasonNumber
        ?: 1
}
