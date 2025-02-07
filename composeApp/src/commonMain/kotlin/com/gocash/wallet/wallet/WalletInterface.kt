package com.gocash.wallet.wallet

// Generar entropy seguro (16, 20, 24, 28, o 32 bytes)

expect fun ByteArray.fillSecureRandom()

fun generateEntropy(strength: Int = 32): ByteArray {
    return ByteArray(strength).apply {
        fillSecureRandom() // Implementación multiplataforma segura
    }
}

interface WalletInterface {
    fun generateMnemonic(entropy: ByteArray): List<String>

    fun determinateSeed(
        mnemonic: List<String>
    ): String

    fun determinateAddress(
        privateKeyHex: String
    ): String

    fun deriveKeyFromMnemonic(
        mnemonic: List<String>,
        passphrase: String = ""
    ): Pair<ByteArray, ByteArray>

    fun signMessage(privateKeyHex: String, message: String): ByteArray?

    fun verifyMessage(
        publicKey: ByteArray,
        message: String,
        signature: ByteArray
    ): Boolean
}
