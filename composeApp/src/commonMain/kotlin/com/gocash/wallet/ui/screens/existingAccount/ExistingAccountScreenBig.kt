package com.gocash.wallet.ui.screens.existingAccount

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gocash.wallet.ui.navigation.NavLinks.HOME
import com.gocash.wallet.ui.navigation.NavLinks.goGenerating
import com.gocash.wallet.ui.screens.existingAccount.components.DisplayInfo
import com.gocash.wallet.ui.screens.existingAccount.components.EnterMnemonicBig
import com.gocash.wallet.ui.screens.register.components.SelectAccountName
import com.gocash.wallet.ui.screens.register.components.SelectAccountPassword
import com.gocash.wallet.ui.screens.register.components.StepProgress

@Composable
fun ExistingAccountScreenBig(
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
                    modifier = Modifier.padding(horizontal = 16.dp).weight(1F).widthIn(
                        0.dp,
                        if (addExistingFormStep == AddExistingFormStep.MNEMONIC) 800.dp else 450.dp
                    )
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Spacer(Modifier.weight(1F))
                    Surface(color = Color.Transparent, modifier = Modifier) {
                        AnimatedVisibility(visible = addExistingFormStep == AddExistingFormStep.MNEMONIC) {
                            EnterMnemonicBig(
                                modifier = Modifier,
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

                                val accountName =
                                    addExistingFormData[AddExistingFormStep.ACCOUNT_NAME]
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
                    Spacer(Modifier.weight(1F))
                }
            }

            // RIGHT
            // RIGHT
            Column(
                modifier = Modifier.fillMaxHeight()
                    .weight(
                        if (addExistingFormStep == AddExistingFormStep.MNEMONIC) 0.5F else 1F
                    )
                    .padding(start = 25.dp, end = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                DisplayInfo(Modifier, addExistingFormStep) {
                    navHostController.navigate(it)
                }

                StepProgress(
                    modifier = Modifier,
                    currentStep = addExistingFormStep.value,
                    count = 3
                )
            }
        }
    }
}