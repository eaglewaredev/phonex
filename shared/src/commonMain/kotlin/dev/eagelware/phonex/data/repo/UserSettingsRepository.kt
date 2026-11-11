package dev.eagelware.phonex.data.repo

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

data class UserSettings(
    val email: String,
    val isLoggedIn: Boolean,
    val darkTheme: Boolean,
    val passcode: String,
)

interface UserSettingsStore{
    fun getSettings() : Flow<UserSettings>

    suspend fun saveSettings(settings : UserSettings)

    suspend fun deleteUserSettings()

    companion object{
        val EMAIL = stringPreferencesKey("email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val PASSCODE = stringPreferencesKey("passcode")
    }

}

//@Singleton
class UserSettingsRepository(private val dataStore: DataStore<Preferences>): UserSettingsStore {
    override fun getSettings() : Flow<UserSettings> =
        dataStore.data.map{preference ->
            val email = preference[UserSettingsStore.EMAIL] ?: ""
            val isLoggedIn = preference[UserSettingsStore.IS_LOGGED_IN] ?: false
            val darkTheme = preference[UserSettingsStore.DARK_THEME] ?: false
            val passcode = preference[UserSettingsStore.PASSCODE] ?: ""
            UserSettings(email,isLoggedIn, darkTheme, passcode)
        }

    override suspend fun saveSettings(settings : UserSettings){
        dataStore.edit{preference ->
            preference[UserSettingsStore.EMAIL] = settings.email
            preference[UserSettingsStore.IS_LOGGED_IN] = settings.isLoggedIn
            preference[UserSettingsStore.DARK_THEME] = settings.darkTheme
            preference[UserSettingsStore.PASSCODE] = settings.passcode
        }
    }

    override suspend fun deleteUserSettings() {
        TODO("Not yet implemented")
    }

    suspend fun deleteAccountSettings() {
        dataStore.edit { preference ->
            preference.remove(UserSettingsStore.EMAIL)
            preference.remove(UserSettingsStore.IS_LOGGED_IN)
            preference.remove(UserSettingsStore.PASSCODE)
        }
    }

    val email: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[UserSettingsStore.EMAIL] ?: ""
        }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[UserSettingsStore.EMAIL] = email
        }
    }

    val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[UserSettingsStore.IS_LOGGED_IN] ?: false
        }

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[UserSettingsStore.IS_LOGGED_IN] = isLoggedIn
        }
    }

    val darkTheme: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[UserSettingsStore.DARK_THEME] ?: false
        }

    suspend fun saveDarkTheme(darkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[UserSettingsStore.DARK_THEME] = darkTheme
        }
    }

    val passcode: Flow<String> =
        dataStore.data.map {preferences ->
            preferences[UserSettingsStore.PASSCODE] ?: ""
        }
    suspend fun savePasscode(passcode: String) {
        dataStore.edit {preferences ->
            preferences[UserSettingsStore.PASSCODE] = passcode
        }
    }
}
