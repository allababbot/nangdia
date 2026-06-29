package com.arisman.nangdia.domain.repository

interface SettingsRepository {
    suspend fun getDarkModeSetting(): Boolean
    suspend fun saveDarkModeSetting(enabled: Boolean)

    suspend fun getCompactLayoutSetting(): Boolean
    suspend fun saveCompactLayoutSetting(enabled: Boolean)

    suspend fun getStreamingNotificationsSetting(): Boolean
    suspend fun saveStreamingNotificationsSetting(enabled: Boolean)
}
