package com.gocash.wallet.ui.screens.existingAccount

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.navigation.NavLinks.HOME
import com.gocash.wallet.ui.navigation.NavLinks.goGenerating
import com.gocash.wallet.ui.screens.existingAccount.components.DisplayInfo
import com.gocash.wallet.ui.screens.existingAccount.components.EnterMnemonic
import com.gocash.wallet.ui.screens.register.RegisterFormStep
import com.gocash.wallet.ui.screens.register.components.SelectAccountName
import com.gocash.wallet.ui.screens.register.components.SelectAccountPassword
import com.gocash.wallet.ui.screens.register.components.StepProgress

enum class AddExistingFormStep(val value: Int) {
    MNEMONIC(0),
    ACCOUNT_NAME(1),
    PASSPHRASE(2),
}

@Composable
fun ExistingAccountScreen(
    navHostController: NavHostController,
    viewModel: ExistingAccountViewModel = viewModel { ExistingAccountViewModel() },
) {
    Scaffold(
        modifier = Modifier.imePadding().padding(horizontal = 16.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        var addExistingFormStep by remember {
            mutableStateOf(AddExistingFormStep.MNEMONIC)
        }

        val addExistingFormData = remember {
            mutableMapOf(AddExistingFormStep.MNEMONIC to "")
        }

        Column(
            modifier = Modifier.padding(it)
                .consumeWindowInsets(it).fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().weight(1F)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                DisplayInfo(Modifier.fillMaxWidth(), addExistingFormStep) {
                    navHostController.navigate(it)
                }

                Surface(color = Color.Transparent, modifier = Modifier) {
                    AnimatedVisibility(visible = addExistingFormStep == AddExistingFormStep.MNEMONIC) {
                        EnterMnemonic(
                            modifier = Modifier.weight(1F),
                            onSelected = {
                                addExistingFormData[AddExistingFormStep.MNEMONIC] =
                                    it.joinToString(" ")
                                addExistingFormStep = AddExistingFormStep.ACCOUNT_NAME
                            }
                        )
                    }

                    AnimatedVisibility(visible = addExistingFormStep == AddExistingFormStep.ACCOUNT_NAME) {
                        SelectAccountName(
                            modifier = Modifier.fillMaxWidth(),
                            showBack = true,
                            onBack = {
                                addExistingFormStep = AddExistingFormStep.MNEMONIC
                            }
                        ) {
                            addExistingFormData[AddExistingFormStep.ACCOUNT_NAME] = it
                            addExistingFormStep = AddExistingFormStep.PASSPHRASE
                        }
                    }

                    AnimatedVisibility(visible = addExistingFormStep == AddExistingFormStep.PASSPHRASE) {
                        SelectAccountPassword(
                            modifier = Modifier.fillMaxWidth(),
                            onBack = {
                                addExistingFormStep = AddExistingFormStep.ACCOUNT_NAME
                            }
                        ) {
                            addExistingFormData[AddExistingFormStep.PASSPHRASE] = it

                            val accountName = addExistingFormData[AddExistingFormStep.ACCOUNT_NAME]
                            val password = addExistingFormData[AddExistingFormStep.PASSPHRASE]
                            val mnemonic = addExistingFormData[AddExistingFormStep.MNEMONIC]

                            if (accountName != null && password != null && mnemonic != null)
                                viewModel.completeAccount(
                                    accountName = accountName,
                                    password = password,
                                    mnemonic = mnemonic.split(" "),
                                    onCompleted = {
                                        navHostController.navigate(goGenerating(it))
                                    }
                                )
                            else navHostController.navigate(HOME)
                        }
                    }
                }
            }

            StepProgress(
                modifier = Modifier,
                currentStep = addExistingFormStep.value,
                count = 3
            )
        }
    }
}