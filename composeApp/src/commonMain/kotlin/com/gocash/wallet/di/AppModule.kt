package com.gocash.wallet.di

import com.gocash.wallet.di.z17Singledi.SingletonInitializer
import com.gocash.wallet.ui.shared.picture.Z17BasePictureHeaders
import com.gocash.wallet.wallet.WalletInterface

class AppModule(
    val walletInterface: WalletInterface,
    val preferencesModule: PreferencesModule
) {
    companion object : SingletonInitializer<AppModule>("app")

    init {
        Z17BasePictureHeaders.createInstance { Z17BasePictureHeaders() }
    }
}