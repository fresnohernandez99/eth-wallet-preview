package com.gocash.wallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

object NavDestinations {

    interface AppDestination {
        val route: String
        val screen: @Composable () -> Unit
        val bigScreen: (@Composable () -> Unit)?
        var args: Array<Any?>
    }

    object HomeDest : AppDestination {
        override val route = HOME
        override var args = emptyArray<Any?>()
        override val screen: @Composable () -> Unit = {
            RegisterScreen(
                navHostController = args[0] as NavHostController
            )
        }
        override val bigScreen: @Composable () -> Unit = {
            RegisterScreen(
                navHostController = args[0] as NavHostController
            )
        }
    }

//    object ChatP2PDest : AppDestination {
//        override val route = CHAT_P2P
//        override var args = emptyArray<Any?>()
//        override val screen: @Composable () -> Unit = {
//            ChatP2PScreen(
//                navHostController = args[0] as NavHostController,
//                params = args[1] as String
//            )
//        }
//    }
}