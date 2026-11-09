package dev.eagelware.phonex.data.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.eagelware.phonex.data.repo.AccountRepository
import dev.eagelware.phonex.data.repo.AccountSettings
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Account(
    val email: String = "",
    val subdomain: String = "",
)


interface AccountStateHolder {
    val initializing: StateFlow<Boolean>
    val accountState: StateFlow<AccountSettings>
    fun updateAccount(accountSettings: AccountSettings): Job
    fun deleteAccount(): Job
    fun saveDarkTheme(isDarkTheme: Boolean): Job
    fun savePasscode(passcode: String): Job
}

class AccountStateHolderImpl(
    private val accountRepository: AccountRepository
): ViewModel(), AccountStateHolder {
    private val _initializing = MutableStateFlow(true)
    override val initializing: StateFlow<Boolean> = _initializing
    private val _accountState = MutableStateFlow(
        AccountSettings("",
            false,
            false,
            "",
        )
    )
    override val accountState: StateFlow<AccountSettings> = _accountState

    init {
        fetchAccount()
    }

    fun fetchAccount() = viewModelScope.launch {
        accountRepository.getSettings().collect {it->
            _accountState.update { current ->
                current.copy(
                    email = it.email,
                    isLoggedIn = it.isLoggedIn,
                    darkTheme = it.darkTheme,
                    passcode = it.passcode,
                )
            }
        }
    }

    override fun updateAccount(accountSettings: AccountSettings) = viewModelScope.launch {
        _accountState.update { current ->
            current.copy(
                email = accountSettings.email,
                isLoggedIn = accountSettings.isLoggedIn,
                darkTheme = accountSettings.darkTheme,
                passcode = accountSettings.passcode,
            )
        }
        accountRepository.saveSettings(_accountState.value)
    }

    override fun deleteAccount() = viewModelScope.launch {
        _accountState.update { current ->
            current.copy(
                email = "",
                isLoggedIn = false,
            )
        }
        accountRepository.deleteAccountSettings()
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) = viewModelScope.launch {
        _accountState.update { current->
            current.copy(
                darkTheme = isDarkTheme,
            )
        }
        accountRepository.saveDarkTheme(isDarkTheme)
    }

    override fun savePasscode(passcode: String) = viewModelScope.launch {
        _accountState.update { current->
            current.copy(
                passcode = passcode
            )
        }
        accountRepository.savePasscode(passcode)
    }
}