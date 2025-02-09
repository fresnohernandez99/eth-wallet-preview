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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.screens.home.components.HomeDashboard
import com.gocash.wallet.ui.screens.home.components.PasswordError
import com.gocash.wallet.ui.screens.home.components.PasswordRequest
import com.gocash.wallet.ui.screens.home.components.SelectFirstAction
import com.gocash.wallet.ui.shared.FullLoading

enum class HomeState {
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
    val homeState by viewModel.homeState.collectAsState()
    val accountData by viewModel.appModule.preferencesModule.getAccountDataFlow()
        .collectAsState(null)

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().imePadding()
    ) {
        when (homeState) {
            HomeState.LOADING -> {
                FullLoading()
            }

            HomeState.REQUEST_PASSWORD -> PasswordRequest(
                Modifier.fillMaxWidth().padding(16.dp),
                onNavigate = {
                    navHostController.navigate(it)
                },
                onCheckPassword = viewModel::validatePassword
            )

            HomeState.PASSWORD_ERROR -> PasswordError(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                onNavigate = {
                    navHostController.navigate(it)
                },
                onSelect = {
                    if (it == 1) viewModel.tryPasswordAgain()
                }
            )

            HomeState.LOGGED -> HomeDashboard(
                modifier = Modifier.fillMaxSize(),
                accountName = accountData?.accountName ?: ""
            )

            HomeState.NEW_USER -> {
                SelectFirstAction(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onSelected = {
                        navHostController.navigate(it)
                    }
                )
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadInitState()
        }
    }
}