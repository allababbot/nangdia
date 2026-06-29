package com.arisman.nangdia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arisman.nangdia.data.local.dao.WatchlistDao
import com.arisman.nangdia.data.local.entity.WatchlistEntity

@Database(
    entities = [WatchlistEntity::class],
    version = 4,
    exportSchema = false
)
abstract class PilemDatabase : RoomDatabase() {
    abstract val watchlistDao: WatchlistDao

    companion object {
        const val DATABASE_NAME = "pilem_db"
    }
}

