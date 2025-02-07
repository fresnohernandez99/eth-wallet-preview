package com.gocash.wallet

import androidx.compose.ui.window.ComposeUIViewController
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.di.PreferencesModule
import com.gocash.wallet.preferences.getDataStore
import com.gocash.wallet.wallet.WalletInterface

fun MainViewController(walletInterface: WalletInterface) = ComposeUIViewController {
    App(
        appModule = AppModule(
            walletInterface = walletInterface,
            preferencesModule = PreferencesModule(dataStore = getDataStore())
        )
    )
}