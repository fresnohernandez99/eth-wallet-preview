package com.gocash.wallet.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.screens.home.components.SelectFirstAction
import com.gocash.wallet.ui.shared.FullLoading

@Composable
fun HomeScreenBig(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
) {
    var initState by remember { mutableStateOf(InitState.LOADING) }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        when (initState) {
            InitState.LOADING -> {
                FullLoading()
            }

            InitState.REQUEST_PASSWORD -> TODO()

            InitState.LOGGED -> {
                TODO()
            }

            InitState.NEW_USER -> {
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
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitState(
            onLogged = { initState = InitState.LOGGED },
            onNewUser = { initState = InitState.NEW_USER }
        )
    }
}