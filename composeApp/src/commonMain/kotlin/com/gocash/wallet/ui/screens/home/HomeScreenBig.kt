package com.gocash.wallet.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.screens.home.components.HomeDashboardBig
import com.gocash.wallet.ui.screens.home.components.PasswordError
import com.gocash.wallet.ui.screens.home.components.PasswordRequest
import com.gocash.wallet.ui.screens.home.components.SelectFirstAction
import com.gocash.wallet.ui.shared.FullLoading

@Composable
fun HomeScreenBig(
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

            HomeState.REQUEST_PASSWORD -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(Modifier.widthIn(0.dp, 450.dp).padding(16.dp)) {
                        PasswordRequest(
                            Modifier.fillMaxWidth(),
                            onNavigate = {
                                navHostController.navigate(it)
                            },
                            onCheckPassword = viewModel::validatePassword
                        )
                    }
                }
            }

            HomeState.PASSWORD_ERROR -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(Modifier.widthIn(0.dp, 450.dp).padding(16.dp)) {
                    PasswordError(
                        modifier = Modifier.fillMaxWidth(),
                        onNavigate = {
                            navHostController.navigate(it)
                        },
                        onSelect = {
                            if (it == 1) viewModel.tryPasswordAgain()
                        }

                    )
                }
            }

            HomeState.LOGGED -> HomeDashboardBig(
                Modifier.fillMaxSize(),
                accountName = accountData?.accountName ?: ""
            )

            HomeState.NEW_USER -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SelectFirstAction(
                        modifier = Modifier.padding(16.dp).widthIn(0.dp, 450.dp),
                        onSelected = {
                            navHostController.navigate(it)
                        }
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadInitState()
        }
    }
}