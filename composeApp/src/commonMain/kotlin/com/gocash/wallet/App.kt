package com.gocash.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gocash.wallet.wallet.WalletInterface
import com.gocash.wallet.wallet.generateEntropy
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalStdlibApi::class)
@Composable
@Preview
fun App(walletInterface: WalletInterface) {
    MaterialTheme {

        val mnemonic = remember {
            walletInterface.generateMnemonic(generateEntropy(18))
            listOf(
                "salmon",
                "script",
                "rally",
                "survey",
                "cactus",
                "unusual",
                "spoil",
                "lava",
                "oil",
                "decrease",
                "raise",
                "supreme",
            )

//            listOf(
//                "fly",
//                "citizen",
//                "exile",
//                "side",
//                "emerge",
//                "episode",
//                "olive",
//                "advance",
//                "crush",
//                "shy",
//                "recycle",
//                "bench",
//                "weird",
//                "metal",
//                "color",
//                "disorder",
//                "local",
//                "replace",
//                "alter",
//                "proof",
//                "sing",
//                "slot",
//                "push",
//                "any"
//            )
        }

        val address = remember {
            val (privateKey, publicKey) = walletInterface.deriveKeyFromMnemonic(mnemonic)
            walletInterface.determinateSeed(mnemonic)

            walletInterface.determinateAddress(privateKey.toHexString())
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(address)
        }
    }
}