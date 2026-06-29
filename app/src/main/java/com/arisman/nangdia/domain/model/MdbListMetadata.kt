package com.arisman.nangdia.domain.model

data class MdbListMetadata(
    val id: Int,
    val name: String,
    val slug: String,
    val user: String,
    val itemsCount: Int,
    val likes: Int,
    val mediatype: String, // "movie", "show", or "mixed"
    val updated: String?,
    val description: String? = null
)
