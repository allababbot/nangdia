package com.arisman.nangdia.di

import com.arisman.nangdia.data.repository.MdbListRepositoryImpl
import com.arisman.nangdia.data.repository.WatchlistRepositoryImpl
import com.arisman.nangdia.data.repository.SettingsRepositoryImpl
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.domain.repository.WatchlistRepository
import com.arisman.nangdia.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMdbListRepository(
        mdbListRepositoryImpl: MdbListRepositoryImpl
    ): MdbListRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(
        watchlistRepositoryImpl: WatchlistRepositoryImpl
    ): WatchlistRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}
