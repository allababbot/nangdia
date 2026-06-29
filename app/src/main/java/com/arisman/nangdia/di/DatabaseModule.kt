package com.arisman.nangdia.di

import android.app.Application
import androidx.room.Room
import com.arisman.nangdia.data.local.PilemDatabase
import com.arisman.nangdia.data.local.dao.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePilemDatabase(app: Application): PilemDatabase {
        return Room.databaseBuilder(
            app,
            PilemDatabase::class.java,
            PilemDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWatchlistDao(db: PilemDatabase): WatchlistDao {
        return db.watchlistDao
    }
}

