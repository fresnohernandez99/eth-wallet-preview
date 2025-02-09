package com.gocash.wallet.ui.screens.generating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.navigation.NavLinks.HOME
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.creating_keys
import gowallet.composeapp.generated.resources.creating_wallet
import gowallet.composeapp.generated.resources.error_with_account
import gowallet.composeapp.generated.resources.reading_data
import gowallet.composeapp.generated.resources.saving_data
import gowallet.composeapp.generated.resources.saying_hello
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.stringResource

enum class ProcessState {
    READING_DATA,
    CREATING_KEYS,
    CREATING_WALLET,
    SAVING_DATA,
    SAYING_HELLO,
    ERROR
}

@Composable
fun GeneratingScreen(
    navHostController: NavHostController,
    viewModel: GeneratingViewModel = viewModel { GeneratingViewModel() },
    params: String
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        val state by viewModel.state.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = when (state) {
                    ProcessState.READING_DATA -> stringResource(Res.string.reading_data)
                    ProcessState.CREATING_KEYS -> stringResource(Res.string.creating_keys)
                    ProcessState.CREATING_WALLET -> stringResource(Res.string.creating_wallet)
                    ProcessState.SAVING_DATA -> stringResource(Res.string.saving_data)
                    ProcessState.SAYING_HELLO -> stringResource(Res.string.saying_hello)
                    ProcessState.ERROR -> stringResource(Res.string.error_with_account)
                },
                style = MaterialTheme.typography.titleSmall
            )
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(
                viewModel.appModule.gson.fromJson(params, GeneratingParams::class),
                onComplete = {
                    runBlocking(Dispatchers.Main) {
                        navHostController.navigate(HOME) {
                            popUpTo(HOME)
                        }
                    }
                },
                onError = {
                    runBlocking(Dispatchers.Main) {
                        navHostController.navigate(HOME) {
                            popUpTo(HOME)
                        }
                    }
                }
            )
        }
    }
}