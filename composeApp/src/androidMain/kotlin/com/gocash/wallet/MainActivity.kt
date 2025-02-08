package com.gocash.wallet

import AndroidWalletInterface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.di.PreferencesModule
import com.gocash.wallet.preferences.getDataStore
import com.gocash.wallet.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val androidWalletInterface = AndroidWalletInterface()

        val appModule = AppModule.createInstance {
            AppModule(
                walletInterface = androidWalletInterface,
                preferencesModule = PreferencesModule(
                    dataStore = getDataStore(this)
                )
            )
        }

        setContent {
            App(

            )
        }
    }
}