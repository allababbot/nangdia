package com.arisman.nangdia.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.arisman.nangdia.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        "nangdia_preferences",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_DARK_MODE = "dark_mode_enabled"
        private const val KEY_COMPACT_LAYOUT = "compact_layout_enabled"
        private const val KEY_STREAMING_NOTIFICATIONS = "streaming_notifications_enabled"
    }

    override suspend fun getDarkModeSetting(): Boolean {
        return sharedPref.getBoolean(KEY_DARK_MODE, false)
    }

    override suspend fun saveDarkModeSetting(enabled: Boolean) {
        sharedPref.edit { putBoolean(KEY_DARK_MODE, enabled) }
    }

    override suspend fun getCompactLayoutSetting(): Boolean {
        return sharedPref.getBoolean(KEY_COMPACT_LAYOUT, false)
    }

    override suspend fun saveCompactLayoutSetting(enabled: Boolean) {
        sharedPref.edit { putBoolean(KEY_COMPACT_LAYOUT, enabled) }
    }

    override suspend fun getStreamingNotificationsSetting(): Boolean {
        return sharedPref.getBoolean(KEY_STREAMING_NOTIFICATIONS, true)
    }

    override suspend fun saveStreamingNotificationsSetting(enabled: Boolean) {
        sharedPref.edit { putBoolean(KEY_STREAMING_NOTIFICATIONS, enabled) }
    }
}
