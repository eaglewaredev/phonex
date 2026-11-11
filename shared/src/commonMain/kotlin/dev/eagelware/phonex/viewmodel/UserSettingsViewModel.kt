package dev.eagelware.phonex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.eagelware.phonex.data.repo.UserSettings
import dev.eagelware.phonex.data.repo.UserSettingsRepository
import dev.eagelware.phonex.data.repo.UserSettingsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserSettingsViewModel(
    private val userSettingsRepository: UserSettingsRepository,
): ViewModel(), UserSettingsStore {
    private val _initializing = MutableStateFlow(true)
    val initializing: StateFlow<Boolean> = _initializing

    private val _userSettings = MutableStateFlow(
        UserSettings("",false,false,"")
    )

    val userSettings: StateFlow<UserSettings> = _userSettings

    init {
        fetchUserSettings()
    }

    fun fetchUserSettings() = viewModelScope.launch {
        userSettingsRepository.getSettings().collect { it->
            _userSettings.update { current->
                current.copy(
                    email = it.email,
                    isLoggedIn = it.isLoggedIn,
                    darkTheme = it.darkTheme,
                    passcode = it.passcode,
                )
            }
        }
    }

    fun updateAccount(accountSettings: UserSettings) = viewModelScope.launch {
        _userSettings.update { current ->
            current.copy(
                email = accountSettings.email,
                isLoggedIn = accountSettings.isLoggedIn,
                darkTheme = accountSettings.darkTheme,
                passcode = accountSettings.passcode,
            )
        }
        userSettingsRepository.saveSettings(_userSettings.value)
    }

    fun deleteAccount() = viewModelScope.launch {
        _userSettings.update { current ->
            current.copy(
                email = "",
                isLoggedIn = false,
            )
        }
        userSettingsRepository.deleteAccountSettings()
    }

    fun saveDarkTheme(isDarkTheme: Boolean) = viewModelScope.launch {
        _userSettings.update { current->
            current.copy(
                darkTheme = isDarkTheme,
            )
        }
        userSettingsRepository.saveDarkTheme(isDarkTheme)
    }

    fun savePasscode(passcode: String) = viewModelScope.launch {
        _userSettings.update { current->
            current.copy(
                passcode = passcode
            )
        }
        userSettingsRepository.savePasscode(passcode)
    }

    override fun getSettings(): Flow<UserSettings> {
        TODO("Not yet implemented")
    }

    override suspend fun saveSettings(settings: UserSettings) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserSettings() {
        TODO("Not yet implemented")
    }
}