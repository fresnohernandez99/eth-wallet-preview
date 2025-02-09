package com.gocash.wallet.ui.screens.existingAccount.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.screens.existingAccount.AddExistingFormStep
import com.gocash.wallet.ui.shared.picture.Z17BasePicture
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.enter_mnemonic
import gowallet.composeapp.generated.resources.go_cash_banner
import gowallet.composeapp.generated.resources.name_your_account
import gowallet.composeapp.generated.resources.no_have_account_create
import gowallet.composeapp.generated.resources.recovery_phrase
import gowallet.composeapp.generated.resources.type_a_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun DisplayInfo(
    modifier: Modifier = Modifier,
    state: AddExistingFormStep,
    onSelected: (String) -> Unit
) {
    Column(
        modifier
    ) {
        Z17BasePicture(
            modifier = Modifier.size(150.dp).align(alignment = Alignment.CenterHorizontally),
            source = Res.drawable.go_cash_banner
        )

        Text(
            text = when (state) {
                AddExistingFormStep.MNEMONIC -> stringResource(Res.string.recovery_phrase)
                AddExistingFormStep.ACCOUNT_NAME -> stringResource(Res.string.name_your_account)
                AddExistingFormStep.PASSPHRASE -> stringResource(Res.string.type_a_password)
            },
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = when (state) {
                AddExistingFormStep.MNEMONIC -> stringResource(Res.string.recovery_phrase)
                AddExistingFormStep.ACCOUNT_NAME -> ""
                AddExistingFormStep.PASSPHRASE -> stringResource(Res.string.enter_mnemonic)
            },
            style = MaterialTheme.typography.labelLarge
        )

        if (state == AddExistingFormStep.MNEMONIC)
            Text(
                modifier = Modifier.clickable { },
                text = stringResource(Res.string.no_have_account_create),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.secondary)
            )

        Spacer(modifier = Modifier.height(25.dp))
    }
}