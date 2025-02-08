package com.gocash.wallet.ui.screens.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.ui.theme.Other
import com.gocash.wallet.wallet.generateEntropy
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.back
import gowallet.composeapp.generated.resources.continue_label
import gowallet.composeapp.generated.resources.copy_phrase
import gowallet.composeapp.generated.resources.hide
import gowallet.composeapp.generated.resources.show
import gowallet.composeapp.generated.resources.wait_time
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun GenerateMnemonicPhrase(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onCompleted: (List<String>) -> Unit
) {
    Column(modifier) {
        var mnemonicPhrase = remember {
            AppModule.getInstance().walletInterface.generateMnemonic(generateEntropy(16))
        }

        var showWords by remember { mutableStateOf(false) }
        var remainingTime by remember { mutableStateOf(10) }
        var canContinue by remember { mutableStateOf(false) }

        val clipboard = LocalClipboardManager.current

        // Contador regresivo
        LaunchedEffect(Unit) {
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime--
            }
            canContinue = true
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lista de palabras
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(8.dp)
            ) {
                items(mnemonicPhrase.size) { index ->
                    val word = if (showWords) mnemonicPhrase[index] else "*****"
                    Text(
                        text = "${index + 1}. $word",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            // Contador de tiempo
            Text(
                text = "${stringResource(Res.string.wait_time)}: ${remainingTime}",
                style = MaterialTheme.typography.labelLarge,
                color = if (canContinue) Other.green else Other.red
            )

            // Botones de control
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showWords = !showWords },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,// Color del botón
                    ),
                ) {
                    Icon(
                        imageVector = if (!showWords) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showWords) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        if (!showWords) stringResource(Res.string.show) else stringResource(Res.string.hide),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = {
                        clipboard.setText(AnnotatedString(mnemonicPhrase.joinToString(" " )))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,// Color del botón
                    ),
                ) {
                    Text(
                        stringResource(Res.string.copy_phrase),
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }

        Spacer(Modifier.height(25.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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

            Button(
                modifier = Modifier.weight(1F),
                enabled = canContinue,
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
                onClick = {
                    onCompleted(mnemonicPhrase)
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