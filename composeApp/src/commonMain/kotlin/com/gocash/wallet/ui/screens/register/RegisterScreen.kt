package com.gocash.wallet.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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

enum class RegisterFormStep(val value: Int) {
    ACCOUNT_NAME(0),
    PASSPHRASE(1),
    MNEMONIC(2),
    CHECK_MNEMONIC(3)
}

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel { RegisterViewModel() },
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(it)
                .consumeWindowInsets(it).fillMaxSize()
        ) {
            var registerFormState by remember {
                mutableStateOf(RegisterFormStep.ACCOUNT_NAME)
            }

            val registerFormData = remember {
                mutableMapOf(RegisterFormStep.ACCOUNT_NAME to "")
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().weight(1F)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                DisplayInfo(Modifier, registerFormState)

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
            }

            StepProgress(
                modifier = Modifier,
                currentStep = registerFormState.value
            )
        }

        LaunchedEffect(Unit) {
        }
    }
}