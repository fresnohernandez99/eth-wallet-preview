package com.gocash.wallet.di

import com.gocash.wallet.data.AccountSecretData
import com.gocash.wallet.di.z17Singledi.SingletonInitializer
import com.gocash.wallet.ui.shared.picture.Z17BasePictureHeaders
import com.gocash.wallet.util.Gson
import com.gocash.wallet.wallet.WalletInterface
import kotlinx.datetime.Clock

class AppModule(
    val walletInterface: WalletInterface,
    val preferencesModule: PreferencesModule
) {
    companion object : SingletonInitializer<AppModule>("app")

    var accountSecretData = AccountSecretData()

    init {
        Z17BasePictureHeaders.createInstance { Z17BasePictureHeaders() }
    }

    val gson = Gson()

    suspend fun loadPrivateData() {
        val privateKeyHex = preferencesModule.getAccountData()?.privateKeyHex

        privateKeyHex?.let {
            val publicAddress = walletInterface.determinateAddress(privateKeyHex)

            accountSecretData = AccountSecretData(
                publicAddress = publicAddress
            )
        }
    }

    suspend fun checkPassword(possiblePassword: String): Boolean {
        val account = preferencesModule.getAccountData()

        if (possiblePassword == account?.password) {
            preferencesModule.setAccountData(
                account.copy(
                    lastLogin = Clock.System.now().toEpochMilliseconds()
                )
            )

            return true
        } else {
            return false
        }

    }
}
