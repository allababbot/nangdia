package com.arisman.nangdia.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arisman.nangdia.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsState(
    val isDarkModeEnabled: Boolean = false,
    val isCompactLayoutEnabled: Boolean = false,
    val areStreamingNotificationsEnabled: Boolean = true,
    val isLoading: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val darkMode = settingsRepository.getDarkModeSetting()
                val compactLayout = settingsRepository.getCompactLayoutSetting()
                val notifications = settingsRepository.getStreamingNotificationsSetting()
                _state.value = _state.value.copy(
                    isDarkModeEnabled = darkMode,
                    isCompactLayoutEnabled = compactLayout,
                    areStreamingNotificationsEnabled = notifications,
                    isLoading = false
                )
            } catch (e: Exception) {
                // TODO: Handle error properly (e.g., log or expose error state)
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveDarkModeSetting(enabled)
            _state.value = _state.value.copy(isDarkModeEnabled = enabled)
        }
    }

    fun toggleCompactLayout(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveCompactLayoutSetting(enabled)
            _state.value = _state.value.copy(isCompactLayoutEnabled = enabled)
        }
    }

    fun toggleStreamingNotifications(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveStreamingNotificationsSetting(enabled)
            _state.value = _state.value.copy(areStreamingNotificationsEnabled = enabled)
        }
    }
}
