package com.arisman.nangdia.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImdbAwardsResponse(
    val stats: ImdbAwardsStats? = null
)

@Serializable
data class ImdbAwardsStats(
    val nominationCount: Int = 0,
    val winCount: Int = 0
)
