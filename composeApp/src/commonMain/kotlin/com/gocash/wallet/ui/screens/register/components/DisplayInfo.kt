package com.gocash.wallet.ui.screens.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.screens.register.RegisterFormStep
import com.gocash.wallet.ui.shared.picture.Z17BasePicture
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.check_list
import gowallet.composeapp.generated.resources.go_cash_banner
import gowallet.composeapp.generated.resources.mnemonic_phrase_description
import gowallet.composeapp.generated.resources.name_your_account
import gowallet.composeapp.generated.resources.password_description
import gowallet.composeapp.generated.resources.read_more
import gowallet.composeapp.generated.resources.select_the_correct_word_from_your_list
import gowallet.composeapp.generated.resources.this_is_important
import gowallet.composeapp.generated.resources.type_a_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun DisplayInfo(
    modifier: Modifier = Modifier,
    registerFormStep: RegisterFormStep
) {
    Column(modifier.fillMaxWidth()) {
        Z17BasePicture(
            modifier = Modifier.size(200.dp).align(alignment = Alignment.CenterHorizontally),
            source = Res.drawable.go_cash_banner
        )

        Text(
            text = when (registerFormStep) {
                RegisterFormStep.ACCOUNT_NAME -> stringResource(Res.string.name_your_account)
                RegisterFormStep.PASSPHRASE -> stringResource(Res.string.type_a_password)
                RegisterFormStep.MNEMONIC -> stringResource(Res.string.this_is_important)
                RegisterFormStep.CHECK_MNEMONIC -> stringResource(Res.string.check_list)
            },
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = when (registerFormStep) {
                RegisterFormStep.ACCOUNT_NAME -> ""
                RegisterFormStep.PASSPHRASE -> stringResource(Res.string.password_description)
                RegisterFormStep.MNEMONIC -> stringResource(Res.string.mnemonic_phrase_description)
                RegisterFormStep.CHECK_MNEMONIC -> stringResource(Res.string.select_the_correct_word_from_your_list)
            },
            style = MaterialTheme.typography.labelLarge
        )

        if (registerFormStep == RegisterFormStep.MNEMONIC)
            Text(
                modifier = Modifier.clickable { },
                text = stringResource(Res.string.read_more),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
            )

        Spacer(modifier = Modifier.height(25.dp))
    }
}