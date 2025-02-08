package com.gocash.wallet.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.navigation.NavLinks.REGISTER
import com.gocash.wallet.ui.shared.FullLoading

enum class InitState {
    LOADING,
    LOGGED,
    NEW_USER
}

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
) {
    var initState by remember { mutableStateOf(InitState.LOADING) }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        when (initState) {
            InitState.LOADING -> {
                FullLoading()
            }

            InitState.LOGGED -> {
                TODO()
            }

            InitState.NEW_USER -> {
                navHostController.navigate(REGISTER)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitState(
            onLogged = { initState = InitState.LOGGED },
            onNewUser = { initState = InitState.NEW_USER }
        )
    }
}