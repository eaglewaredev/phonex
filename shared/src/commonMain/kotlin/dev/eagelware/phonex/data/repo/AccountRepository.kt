package dev.eagelware.phonex.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Singleton

data class AccountSettings(
    val email: String,
    val isLoggedIn: Boolean,
    val darkTheme: Boolean,
    val passcode: String,
)

interface PreferenceStore{
    fun getSettings() : Flow<AccountSettings>

    suspend fun saveSettings(settings : AccountSettings)

    suspend fun deleteAccountSettings()

    companion object{
        val EMAIL = stringPreferencesKey("email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val PASSCODE = stringPreferencesKey("passcode")
    }

}

//@Singleton
class AccountRepository(private val dataStore: DataStore<Preferences>): PreferenceStore {
    override fun getSettings() : Flow<AccountSettings> =
        dataStore.data.map{preference ->
            val email = preference[PreferenceStore.EMAIL] ?: ""
            val isLoggedIn = preference[PreferenceStore.IS_LOGGED_IN] ?: false
            val darkTheme = preference[PreferenceStore.DARK_THEME] ?: false
            val passcode = preference[PreferenceStore.PASSCODE] ?: ""
            AccountSettings(email,isLoggedIn, darkTheme, passcode)
        }

    override suspend fun saveSettings(settings : AccountSettings){
        dataStore.edit{preference ->
            preference[PreferenceStore.EMAIL] = settings.email
            preference[PreferenceStore.IS_LOGGED_IN] = settings.isLoggedIn
            preference[PreferenceStore.DARK_THEME] = settings.darkTheme
            preference[PreferenceStore.PASSCODE] = settings.passcode
        }
    }

    override suspend fun deleteAccountSettings() {
        dataStore.edit { preference ->
            preference.remove(PreferenceStore.EMAIL)
            preference.remove(PreferenceStore.IS_LOGGED_IN)
            preference.remove(PreferenceStore.PASSCODE)
        }
    }

    val userName: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[PreferenceStore.USERNAME] ?: ""
        }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceStore.USERNAME] = userName
        }
    }

    val isLoggedIn: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[PreferenceStore.IS_LOGGED_IN] ?: false
        }

    suspend fun saveIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceStore.IS_LOGGED_IN] = isLoggedIn
        }
    }

    val darkTheme: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[PreferenceStore.DARK_THEME] ?: false
        }

    suspend fun saveDarkTheme(darkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceStore.DARK_THEME] = darkTheme
        }
    }

    val passcode: Flow<String> =
        dataStore.data.map {preferences ->
            preferences[PreferenceStore.PASSCODE] ?: ""
        }
    suspend fun savePasscode(passcode: String) {
        dataStore.edit {preferences ->
            preferences[PreferenceStore.PASSCODE] = passcode
        }
    }
}
