package com.gocash.wallet.data

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
class AccountData(
    val accountName: String,
    val password: String,
    val privateKeyHex: String
)