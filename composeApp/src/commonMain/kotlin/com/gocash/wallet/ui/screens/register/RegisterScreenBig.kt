package com.gocash.wallet.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.screens.register.components.CheckMnemonicPhrase
import com.gocash.wallet.ui.screens.register.components.DisplayInfo
import com.gocash.wallet.ui.screens.register.components.GenerateMnemonicPhrase
import com.gocash.wallet.ui.screens.register.components.SelectAccountName
import com.gocash.wallet.ui.screens.register.components.SelectAccountPassword
import com.gocash.wallet.ui.screens.register.components.StepProgress

@Composable
fun RegisterScreenBig(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel { RegisterViewModel() },
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        var registerFormState by remember {
            mutableStateOf(RegisterFormStep.ACCOUNT_NAME)
        }

        val registerFormData = remember {
            mutableMapOf(RegisterFormStep.ACCOUNT_NAME to "")
        }

        Row(
            modifier = Modifier.padding(it)
                .consumeWindowInsets(it).fillMaxSize()
        ) {
            // LEFT
            Column(
                modifier = Modifier.fillMaxHeight().weight(1F).padding(start = 50.dp, end = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).weight(1F).widthIn(0.dp, 450.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Spacer(Modifier.weight(1F))
                    Surface(color = Color.Transparent, modifier = Modifier) {
                        AnimatedVisibility(visible = registerFormState == RegisterFormStep.ACCOUNT_NAME) {
                            SelectAccountName(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                registerFormData[RegisterFormStep.ACCOUNT_NAME] = it
                                registerFormState = RegisterFormStep.PASSPHRASE
                            }
                        }

                        AnimatedVisibility(visible = registerFormState == RegisterFormStep.PASSPHRASE) {
                            SelectAccountPassword(
                                modifier = Modifier.fillMaxWidth(),
                                onBack = {
                                    registerFormState = RegisterFormStep.ACCOUNT_NAME
                                }
                            ) {
                                registerFormData[RegisterFormStep.PASSPHRASE] = it
                                registerFormState = RegisterFormStep.MNEMONIC
                            }
                        }

                        AnimatedVisibility(visible = registerFormState == RegisterFormStep.MNEMONIC) {
                            GenerateMnemonicPhrase(
                                modifier = Modifier.fillMaxWidth(),
                                onBack = {
                                    registerFormState = RegisterFormStep.PASSPHRASE
                                }
                            ) {
                                registerFormData[RegisterFormStep.PASSPHRASE] = it.joinToString(" ")
                                registerFormState = RegisterFormStep.CHECK_MNEMONIC
                            }
                        }

                        AnimatedVisibility(visible = registerFormState == RegisterFormStep.CHECK_MNEMONIC) {
                            CheckMnemonicPhrase(
                                modifier = Modifier.fillMaxWidth(),
                                currentList = registerFormData[RegisterFormStep.PASSPHRASE]?.split(" ")
                                    ?: emptyList(),
                                onBack = {
                                    registerFormState = RegisterFormStep.MNEMONIC
                                }
                            ) {

                            }
                        }
                    }
                    Spacer(Modifier.weight(1F))
                }
            }

            // RIGHT
            Column(
                modifier = Modifier.fillMaxHeight().weight(1F).padding(start = 25.dp, end = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                DisplayInfo(Modifier, registerFormState)

                StepProgress(
                    modifier = Modifier,
                    currentStep = registerFormState.value
                )
            }
        }



        LaunchedEffect(Unit) {
        }
    }
}