package com.gocash.wallet.ui.screens.generating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gocash.wallet.data.AccountData
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.util.launchWithContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GeneratingViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProcessState.READING_DATA)
    val state = _state.asStateFlow()

    val appModule = AppModule.getInstance()

    @OptIn(ExperimentalStdlibApi::class)
    fun loadData(generatingParams: GeneratingParams, onComplete: () -> Unit, onError: () -> Unit) {
        viewModelScope.launchWithContext {

            try {
                delay(1500)
                _state.value = ProcessState.CREATING_KEYS

                val (privateKey, publicKey) = appModule.walletInterface.deriveKeyFromMnemonic(
                    generatingParams.mnemonic
                )
                
                delay(1500)
                _state.value = ProcessState.CREATING_WALLET

                appModule.preferencesModule.setAccountData(AccountData(
                    accountName = generatingParams.accountName,
                    password = generatingParams.password,
                    privateKeyHex = privateKey.toHexString()
                ))

                delay(1000)
                _state.value = ProcessState.SAVING_DATA

                appModule.loadPrivateData()

                delay(500)
                _state.value = ProcessState.SAYING_HELLO

                delay(1000)
                onComplete()
            } catch (e: Exception) {
                onError()
            }
        }
    }
}