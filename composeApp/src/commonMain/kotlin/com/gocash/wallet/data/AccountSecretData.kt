package com.gocash.wallet.data

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
class AccountSecretData(
    val publicAddress: String = ""
)