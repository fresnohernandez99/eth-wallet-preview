package com.gocash.wallet.wallet

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Security.SecRandomCopyBytes
import platform.Security.errSecSuccess

@OptIn(ExperimentalForeignApi::class)
actual fun ByteArray.fillSecureRandom() {
    val result = SecRandomCopyBytes(null, size.toULong(), this.refTo(0))
    check(result == errSecSuccess) { "Error generando bytes aleatorios: $result" }
}