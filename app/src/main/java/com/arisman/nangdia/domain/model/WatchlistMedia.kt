package com.arisman.nangdia.domain.model

data class WatchlistMedia(
    val id: String, // Combination of provider and id, or just mdblistId
    val mediaId: String,
    val title: String,
    val poster: String?,
    val mediaType: String,
    val provider: String,
    val year: Int?,
    val score: Int? = null,
    val released: String? = null,
    val releasedDigital: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)

