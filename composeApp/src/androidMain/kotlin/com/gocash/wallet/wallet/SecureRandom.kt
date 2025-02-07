package com.gocash.wallet.wallet

actual fun ByteArray.fillSecureRandom() {
    val secureRandom = java.security.SecureRandom()
    secureRandom.nextBytes(this)
}