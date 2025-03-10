package com.gocash.wallet.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gocash.wallet.ui.navigation.NavDestinations
import com.gocash.wallet.ui.navigation.NavLinks
import com.gocash.wallet.ui.navigation.navigationComposable
import com.gocash.wallet.ui.theme.AppTheme
import com.gocash.wallet.ui.theme.WindowSize
import com.gocash.wallet.ui.theme.rememberWindowSizeClass
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val windowSize = rememberWindowSizeClass()

    AppTheme(
        bigSize = windowSize == WindowSize.Expanded
    ) {
        val navHostController = rememberNavController()
        val destinations = arrayOf(
            NavDestinations.HomeDest,
            NavDestinations.RegisterDest,
            NavDestinations.ExistingAccountDest
        )

        NavHost(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                .systemBarsPadding(),
            navController = navHostController,
            startDestination = destinations[0].route,
        ) {
            // regular destinations with navHost and no parameters
            destinations.forEach { nav ->
                navigationComposable(route = nav.route) {
                    nav.args = arrayOf(navHostController)

                    when (windowSize) {
                        WindowSize.Compact -> nav.screen()
                        WindowSize.Medium -> nav.screen()
                        WindowSize.Expanded -> nav.bigScreen?.invoke() ?: nav.screen()
                    }
                }
            }

            // other destinations
            navigationComposable(
                route = NavLinks.GENERATING,
                arguments = listOf(
                    navArgument("params") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                content = { bse ->
                    val dest = NavDestinations.GeneratingDest
                    val params = NavLinks.decode(
                        (bse.arguments?.getString("params") ?: "")
                    )

                    dest.args =
                        arrayOf(
                            navHostController,
                            params
                        )

                    when (windowSize) {
                        WindowSize.Compact -> dest.screen()
                        WindowSize.Medium -> dest.screen()
                        WindowSize.Expanded -> dest.bigScreen.invoke()
                    }
                })
        }
    }
}


//{
//    MaterialTheme {
//
////        val mnemonic = remember {
////            walletInterface.generateMnemonic(generateEntropy(18))
////            listOf(
////                "salmon",
////                "script",
////                "rally",
////                "survey",
////                "cactus",
////                "unusual",
////                "spoil",
////                "lava",
////                "oil",
////                "decrease",
////                "raise",
////                "supreme",
////            )
////
//////            listOf(
//////                "fly",
//////                "citizen",
//////                "exile",
//////                "side",
//////                "emerge",
//////                "episode",
//////                "olive",
//////                "advance",
//////                "crush",
//////                "shy",
//////                "recycle",
//////                "bench",
//////                "weird",
//////                "metal",
//////                "color",
//////                "disorder",
//////                "local",
//////                "replace",
//////                "alter",
//////                "proof",
//////                "sing",
//////                "slot",
//////                "push",
//////                "any"
//////            )
////        }
////
////        val address = remember {
////            val (privateKey, publicKey) = walletInterface.deriveKeyFromMnemonic(mnemonic)
////            walletInterface.determinateSeed(mnemonic)
////
////            walletInterface.determinateAddress(privateKey.toHexString())
////        }
//
//        Column(
//            Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//        }
//    }
//}