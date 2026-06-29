package com.arisman.nangdia.domain.repository

import com.arisman.nangdia.domain.model.WatchlistMedia
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getWatchlist(): Flow<List<WatchlistMedia>>
    suspend fun getWatchlistById(id: String): WatchlistMedia?
    suspend fun addToWatchlist(media: WatchlistMedia)
    suspend fun removeFromWatchlist(id: String)
}

