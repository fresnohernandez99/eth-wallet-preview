package com.gocash.wallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.navigation.NavLinks.EXISTING_ACCOUNT
import com.gocash.wallet.ui.navigation.NavLinks.GENERATING
import com.gocash.wallet.ui.navigation.NavLinks.HOME
import com.gocash.wallet.ui.navigation.NavLinks.REGISTER
import com.gocash.wallet.ui.screens.existingAccount.ExistingAccountScreen
import com.gocash.wallet.ui.screens.existingAccount.ExistingAccountScreenBig
import com.gocash.wallet.ui.screens.generating.GeneratingScreen
import com.gocash.wallet.ui.screens.home.HomeScreen
import com.gocash.wallet.ui.screens.home.HomeScreenBig
import com.gocash.wallet.ui.screens.register.RegisterScreen
import com.gocash.wallet.ui.screens.register.RegisterScreenBig

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
            HomeScreen(
                navHostController = args[0] as NavHostController
            )
        }
        override val bigScreen: @Composable () -> Unit = {
            HomeScreenBig(
                navHostController = args[0] as NavHostController
            )
        }
    }

    object RegisterDest : AppDestination {
        override val route = REGISTER
        override var args = emptyArray<Any?>()
        override val screen: @Composable () -> Unit = {
            RegisterScreen(
                navHostController = args[0] as NavHostController
            )
        }
        override val bigScreen: @Composable () -> Unit = {
            RegisterScreenBig(
                navHostController = args[0] as NavHostController
            )
        }
    }

    object ExistingAccountDest : AppDestination {
        override val route = EXISTING_ACCOUNT
        override var args = emptyArray<Any?>()
        override val screen: @Composable () -> Unit = {
            ExistingAccountScreen(
                navHostController = args[0] as NavHostController
            )
        }
        override val bigScreen: @Composable () -> Unit = {
            ExistingAccountScreenBig(
                navHostController = args[0] as NavHostController
            )
        }
    }

    object GeneratingDest : AppDestination {
        override val route = GENERATING
        override var args = emptyArray<Any?>()
        override val screen: @Composable () -> Unit = {
            GeneratingScreen(
                navHostController = args[0] as NavHostController,
                params = args[1] as String
            )
        }
        override val bigScreen: @Composable () -> Unit = {
            GeneratingScreen(
                navHostController = args[0] as NavHostController,
                params = args[1] as String
            )
        }
    }
}