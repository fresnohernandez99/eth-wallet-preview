package com.gocash.wallet.di

import com.gocash.wallet.wallet.WalletInterface

class AppModule(
    val walletInterface: WalletInterface,
    val preferencesModule: PreferencesModule
)