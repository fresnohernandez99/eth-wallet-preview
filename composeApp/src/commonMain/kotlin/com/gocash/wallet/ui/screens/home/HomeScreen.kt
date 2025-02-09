package com.gocash.wallet.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.screens.home.components.HomeDashboard
import com.gocash.wallet.ui.screens.home.components.PasswordRequest
import com.gocash.wallet.ui.screens.home.components.SelectFirstAction
import com.gocash.wallet.ui.shared.FullLoading

enum class InitState {
    LOADING,
    REQUEST_PASSWORD,
    PASSWORD_ERROR,
    LOGGED,
    NEW_USER
}

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
) {
    var initState by remember { mutableStateOf(InitState.LOADING) }
    val accountData by viewModel.appModule.preferencesModule.getAccountDataFlow()
        .collectAsState(null)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().imePadding()
    ) {
        when (initState) {
            InitState.LOADING -> {
                FullLoading()
            }

            InitState.REQUEST_PASSWORD -> PasswordRequest(
                Modifier.fillMaxWidth(),
                onNavigate = {
                    navHostController.navigate(it)
                },
                onCheckPassword = {

                }
            )

            InitState.PASSWORD_ERROR -> {}

            InitState.LOGGED -> HomeDashboard(
                modifier = Modifier.fillMaxSize(),
                accountName = accountData?.accountName ?: ""
            )

            InitState.NEW_USER -> {
                SelectFirstAction(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onSelected = {
                        navHostController.navigate(it)
                    }
                )
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadInitState(
                onLogged = { initState = InitState.LOGGED },
                onNewUser = { initState = InitState.NEW_USER },
                onPasswordIsRequired = { initState = InitState.REQUEST_PASSWORD }
            )
        }
    }
}