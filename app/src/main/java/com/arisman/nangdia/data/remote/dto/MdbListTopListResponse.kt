package com.arisman.nangdia.data.remote.dto

import com.arisman.nangdia.domain.model.MdbListMetadata
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MdbListSummaryDto(
    val id: Int,
    val name: String,
    val slug: String,
    @SerialName("user_name") val user: String = "Unknown",
    @SerialName("items") val itemsCount: Int = 0,
    val likes: Int = 0,
    @SerialName("mediatype") val type: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    val description: String? = null
) {
    fun toMdbListMetadata(): MdbListMetadata {
        return MdbListMetadata(
            id = id,
            name = name,
            slug = slug,
            user = user,
            itemsCount = itemsCount,
            likes = likes,
            mediatype = type ?: "mixed",
            updated = updatedAt,
            description = description
        )
    }
}
