package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImdbParentsGuideResponse(
    val parentsGuide: List<ParentsGuideCategoryDto> = emptyList()
)

@Serializable
data class ParentsGuideCategoryDto(
    val category: String,
    val severityBreakdowns: List<SeverityBreakdownDto> = emptyList(),
    val reviews: List<ParentsGuideReviewDto> = emptyList()
)

@Serializable
data class SeverityBreakdownDto(
    val severityLevel: String,
    val voteCount: Int = 0
)

@Serializable
data class ParentsGuideReviewDto(
    val text: String = "",
    val isSpoiler: Boolean? = null
)

