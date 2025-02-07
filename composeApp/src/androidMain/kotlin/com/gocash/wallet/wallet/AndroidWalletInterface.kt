import cash.z.ecc.android.bip39.Mnemonics
import com.gocash.wallet.wallet.WalletInterface
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.wallet.DeterministicSeed
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.crypto.digests.KeccakDigest
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AndroidWalletInterface : WalletInterface {
    override fun generateMnemonic(entropy: ByteArray): List<String> {
        val mnemonicCode = Mnemonics.MnemonicCode(entropy)
        return mnemonicCode.words.map { String(it) }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun determinateSeed(mnemonic: List<String>): String {
        val seed = DeterministicSeed(mnemonic, null, "", 0L)
        val base64 = Base64.encode(seed.seedBytes!!)
        return base64
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun determinateAddress(privateKeyHex: String): String {
        // 1. Validar y convertir clave privada
        require(privateKeyHex.length == 64) { "Clave privada inválida" }
        val privateKey = BigInteger(privateKeyHex, 16)

        // 2. Configurar curva secp256k1
        val params = SECNamedCurves.getByName("secp256k1")
        val curve = ECDomainParameters(params.curve, params.g, params.n, params.h)
        require(privateKey < curve.n) { "Clave privada fuera de rango" }

        // 3. Generar clave pública NO COMPRIMIDA (0x04 + X + Y)
        val publicKeyPoint = curve.g.multiply(privateKey).normalize()
        val uncompressedKey = publicKeyPoint.getEncoded(false) // 65 bytes

        // 4. Extraer X e Y (64 bytes)
        val rawPublicKey = uncompressedKey.copyOfRange(1, 65)

        // 5. Calcular hash Keccak-256
        val keccak = KeccakDigest(256).apply {
            update(rawPublicKey, 0, rawPublicKey.size)
        }
        val hash = ByteArray(32).apply { keccak.doFinal(this, 0) }

        // 6. Tomar últimos 20 bytes y aplicar checksum
        val addressBytes = hash.copyOfRange(12, 32)
        return applyEIP55Checksum(addressBytes)
    }

    // Implementación mejorada de EIP-55
    @OptIn(ExperimentalStdlibApi::class)
    private fun applyEIP55Checksum(addressBytes: ByteArray): String {
        val address = addressBytes.toHexString()
        val hash = KeccakDigest(256).apply {
            update(address.toByteArray(), 0, address.length)
        }.let {
            ByteArray(32).apply { it.doFinal(this, 0) }.toHexString()
        }

        return "0x${address.mapIndexed { i, c ->
            when {
                hash[i].digitToInt(16) >= 8 -> c.uppercaseChar()
                else -> c.lowercaseChar()
            }
        }.joinToString("")}"
    }


    override fun deriveKeyFromMnemonic(
        mnemonic: List<String>,
        passphrase: String
    ): Pair<ByteArray, ByteArray> {
        // 1. Generar seed BIP-39 (corregido el salt)
        val seed = DeterministicSeed(mnemonic, null, "", 0L)

        // 2. Crear clave maestra BIP-32
        val masterKey = HDKeyDerivation.createMasterPrivateKey(seed.seedBytes)

        // 3. Derivación BIP-44 para Ethereum: m/44'/60'/0'/0/0
        val path = listOf(
            ChildNumber(44, true),   // Purpose (BIP-44)
            ChildNumber(60, true),   // CoinType (ETH)
            ChildNumber(0, true),    // Account
            ChildNumber(0, false),   // Change (external)
            ChildNumber(0, false)    // AddressIndex
        )

        // 4. Derivación recursiva de la clave
        var key = masterKey
        for (childNumber in path) {
            key = HDKeyDerivation.deriveChildKey(key, childNumber)
        }

        // 5. Extraer clave privada (32 bytes, padding izquierdo si es necesario)
        val privateKey = key.privKeyBytes
        val paddedPrivateKey = ByteArray(32).apply {
            System.arraycopy(privateKey, 0, this, 32 - privateKey.size, privateKey.size)
        }

        // 6. Generar clave pública NO COMPRIMIDA (0x04 + X + Y)
        val publicKeyPoint = key.decompress().pubKeyPoint.getEncoded(false)

        return Pair(paddedPrivateKey, publicKeyPoint)
    }


    @OptIn(ExperimentalStdlibApi::class, ExperimentalUnsignedTypes::class)
    override fun signMessage(privateKeyHex: String, message: String): ByteArray? {
        val privateKey = privateKeyHex.hexToUByteArray().toByteArray()

        val key = ECKey.fromPrivate(privateKey)
        val messageHash = Sha256Hash.of(message.toByteArray())

        // Firmar el hash del mensaje
        val signature = key.sign(messageHash)

        return signature.encodeToDER()
    }

    override fun verifyMessage(
        publicKey: ByteArray,
        message: String,
        signature: ByteArray
    ): Boolean {
        return try {
            // Reconstruir la clave pública desde bytes
            val ecKey = ECKey.fromPublicOnly(publicKey)

            // Calcular el hash del mensaje (mismo método que en la firma)
            val messageHash = Sha256Hash.hash(message.toByteArray())

            // Verificar la firma
            ecKey.verify(messageHash, signature)
        } catch (e: Exception) {
            false
        }
    }
}