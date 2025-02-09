package com.gocash.wallet.ui.screens.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.theme.Other
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.account_name
import gowallet.composeapp.generated.resources.back
import gowallet.composeapp.generated.resources.continue_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectAccountName(
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    onSelected: (String) -> Unit
) {
    Column(modifier) {
        var text by remember { mutableStateOf("") }
        val isEnabled by remember {
            derivedStateOf { text.isNotBlank() }
        }

        Text(
            stringResource(Res.string.account_name),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, // Fondo transparente al enfocar
                unfocusedContainerColor = Color.Transparent, // Fondo transparente al no enfocar
                focusedTextColor = MaterialTheme.colorScheme.onBackground,//- the color used for the input text of this text field when focused
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground, //- the color used for the input text of this text field when not focused
                focusedBorderColor = MaterialTheme.colorScheme.primary,//- the border color for this text field when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,//- the border color for this text field when not focused
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (showBack) {
                OutlinedButton(
                    modifier = Modifier.weight(1F),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
                    onClick = {
                        onBack()
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
                        stringResource(Res.string.back),
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground) // Color del texto
                    ) // Texto del botón
                }

                Spacer(Modifier.weight(0.1F).width(10.dp))
            }

            Button(
                modifier = Modifier.weight(1F),
                enabled = isEnabled,
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
                onClick = {
                    onSelected(text)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,// Color del botón
                    disabledContainerColor = Other.grey.copy(alpha = 0.6F)
                ),
                shape = RoundedCornerShape(50) // Bordes redondos al 50%
            ) {
                Text(
                    stringResource(Res.string.continue_label),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                ) // Texto del botón
            }
        }

    }
}