package com.gocash.wallet.ui.screens.register

import androidx.lifecycle.ViewModel
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.ui.screens.generating.GeneratingParams

class RegisterViewModel : ViewModel() {
    val appModule = AppModule.getInstance()

    fun completeAccount(
        accountName: String,
        password: String,
        mnemonic: List<String>,
        onCompleted: (String) -> Unit
    ) {
        val generatingParams = GeneratingParams(
            accountName = accountName,
            password = password,
            mnemonic = mnemonic
        )

        onCompleted(appModule.gson.toJson(generatingParams))
    }
}