package com.arisman.nangdia.data.local.dao

import androidx.room.*
import com.arisman.nangdia.data.local.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE id = :id")
    suspend fun getWatchlistById(id: String): WatchlistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(watchlistEntity: WatchlistEntity)

    @Delete
    suspend fun deleteWatchlist(watchlistEntity: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE id = :id")
    suspend fun deleteWatchlistById(id: String)
}

