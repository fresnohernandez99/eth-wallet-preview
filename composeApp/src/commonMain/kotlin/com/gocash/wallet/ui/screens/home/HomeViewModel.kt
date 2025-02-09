package com.gocash.wallet.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.util.launchWithContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class HomeViewModel : ViewModel() {

    val appModule = AppModule.getInstance()

    fun loadInitState(
        onLogged: () -> Unit,
        onNewUser: () -> Unit,
        onPasswordIsRequired: () -> Unit
    ) {
        viewModelScope.launchWithContext {
            val existingData = appModule.preferencesModule.getAccountData()

            // hay datos de usuario
            if (existingData != null) {
                appModule.loadPrivateData()

                // pasaron 5 min desde la ultima vez requerir contraseña de nevo
                if (hasFiveMinutesPassed(existingData.lastLogin)) {
                    viewModelScope.launch(Dispatchers.Main) {
                        onPasswordIsRequired()
                    }
                }
                // usuario listo
                else {
                    viewModelScope.launch(Dispatchers.Main) {
                        onLogged()
                    }
                }

            }
            // es usuario sin cuenta aún
            else {
                viewModelScope.launch(Dispatchers.Main) {
                    onNewUser()
                }
            }
        }
    }

    fun hasFiveMinutesPassed(lastLogin: Long): Boolean {
        val lastLoginInstant = Instant.fromEpochMilliseconds(lastLogin)
        val currentInstant = Clock.System.now()
        val difference = currentInstant - lastLoginInstant
        return difference.inWholeMinutes >= 5
    }
}