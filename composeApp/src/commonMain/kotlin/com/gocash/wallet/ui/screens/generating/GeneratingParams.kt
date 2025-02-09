package com.gocash.wallet.ui.screens.generating

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
class GeneratingParams(
    val accountName: String,
    val password: String,
    val mnemonic: List<String> = arrayListOf()
)