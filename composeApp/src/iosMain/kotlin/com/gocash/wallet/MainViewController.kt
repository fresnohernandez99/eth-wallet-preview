package com.gocash.wallet

import androidx.compose.ui.window.ComposeUIViewController
import com.gocash.wallet.wallet.WalletInterface

fun MainViewController(walletInterface: WalletInterface) = ComposeUIViewController { App(walletInterface) }