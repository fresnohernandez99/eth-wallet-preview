package com.gocash.wallet.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.util.launchWithContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class HomeViewModel : ViewModel() {
    val appModule = AppModule.getInstance()

    private val _homeState = MutableStateFlow(HomeState.LOADING)
    val homeState = _homeState.asStateFlow()

    fun loadInitState() {
        viewModelScope.launchWithContext {
            val existingData = appModule.preferencesModule.getAccountData()

            // hay datos de usuario
            if (existingData != null) {
                appModule.loadPrivateData()

                // pasaron 5 min desde la ultima vez requerir contraseña de nevo
                if (hasFiveMinutesPassed(existingData.lastLogin)) {
                    viewModelScope.launch(Dispatchers.Main) {
                        _homeState.value = HomeState.REQUEST_PASSWORD
                    }
                }
                // usuario listo
                else {
                    viewModelScope.launch(Dispatchers.Main) {
                        _homeState.value = HomeState.LOGGED
                    }
                }

            }
            // es usuario sin cuenta aún
            else {
                viewModelScope.launch(Dispatchers.Main) {
                    _homeState.value = HomeState.NEW_USER
                }
            }
        }
    }

    private fun hasFiveMinutesPassed(lastLogin: Long): Boolean {
        val lastLoginInstant = Instant.fromEpochMilliseconds(lastLogin)
        val currentInstant = Clock.System.now()
        val difference = currentInstant - lastLoginInstant
        return difference.inWholeMinutes >= 5
    }

    fun validatePassword(possiblePassword: String) {
        viewModelScope.launchWithContext {
            val check = appModule.checkPassword(possiblePassword)

            if (check) {
                _homeState.value = HomeState.LOGGED
            } else
                _homeState.value = HomeState.PASSWORD_ERROR
        }
    }

    fun tryPasswordAgain() {
        _homeState.value = HomeState.REQUEST_PASSWORD
    }
}