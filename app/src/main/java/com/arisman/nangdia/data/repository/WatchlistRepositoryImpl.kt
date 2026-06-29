package com.arisman.nangdia.data.repository

import com.arisman.nangdia.data.local.dao.WatchlistDao
import com.arisman.nangdia.data.local.entity.WatchlistEntity
import com.arisman.nangdia.domain.model.WatchlistMedia
import com.arisman.nangdia.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val dao: WatchlistDao
) : WatchlistRepository {
    override fun getWatchlist(): Flow<List<WatchlistMedia>> {
        return dao.getAllWatchlist().map { entities ->
            entities.map { it.toWatchlistMedia() }
        }
    }

    override suspend fun getWatchlistById(id: String): WatchlistMedia? {
        return dao.getWatchlistById(id)?.toWatchlistMedia()
    }

    override suspend fun addToWatchlist(media: WatchlistMedia) {
        dao.insertWatchlist(WatchlistEntity.fromWatchlistMedia(media))
    }

    override suspend fun removeFromWatchlist(id: String) {
        dao.deleteWatchlistById(id)
    }
}

