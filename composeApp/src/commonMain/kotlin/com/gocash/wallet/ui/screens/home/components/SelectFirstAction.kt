package com.gocash.wallet.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.navigation.NavLinks.REGISTER
import com.gocash.wallet.ui.shared.picture.Z17BasePicture
import com.gocash.wallet.ui.theme.Other
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.add_existing_account
import gowallet.composeapp.generated.resources.create_account
import gowallet.composeapp.generated.resources.go_cash_banner
import gowallet.composeapp.generated.resources.what_to_do
import gowallet.composeapp.generated.resources.what_to_do_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectFirstAction(
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Z17BasePicture(
            modifier = Modifier.size(200.dp).align(alignment = Alignment.CenterHorizontally),
            source = Res.drawable.go_cash_banner
        )

        Text(
            text = stringResource(Res.string.what_to_do),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = stringResource(Res.string.what_to_do_description),
            style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
            onClick = {
                onSelected(REGISTER)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent, // Fondo transparente
                disabledContainerColor = Other.grey.copy(alpha = 0.6F) // Color de fondo deshabilitado
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.onBackground
            ), // Borde del botón
            shape = RoundedCornerShape(50) // Bordes redondos al 50%
        ) {
            Text(
                stringResource(Res.string.create_account),
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground) // Color del texto
            ) // Texto del botón
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
            onClick = {
                onSelected(REGISTER)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent, // Fondo transparente
                disabledContainerColor = Other.grey.copy(alpha = 0.6F) // Color de fondo deshabilitado
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.onBackground
            ), // Borde del botón
            shape = RoundedCornerShape(50) // Bordes redondos al 50%
        ) {
            Text(
                stringResource(Res.string.add_existing_account),
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground) // Color del texto
            ) // Texto del botón
        }

    }
}