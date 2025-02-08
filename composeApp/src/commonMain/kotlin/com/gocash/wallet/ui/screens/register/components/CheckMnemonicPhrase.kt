package com.gocash.wallet.ui.screens.register.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.gocash.wallet.di.AppModule
import com.gocash.wallet.ui.theme.Other
import com.gocash.wallet.wallet.generateEntropy
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.back
import gowallet.composeapp.generated.resources.completed
import gowallet.composeapp.generated.resources.name_your_account
import gowallet.composeapp.generated.resources.position
import gowallet.composeapp.generated.resources.select_the_correct_word_from_your_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun CheckMnemonicPhrase(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    currentList: List<String>,
    onCompleted: () -> Unit
) {
    Column(modifier) {
        var fakeMnemonicPhrase = remember {
            AppModule.getInstance().walletInterface.generateMnemonic(generateEntropy(16))
        }

        val correctSelections = listOf(currentList[2], currentList[6], currentList[8])

        var pos3 by remember {
            mutableStateOf("")
        }

        var pos7 by remember {
            mutableStateOf("")
        }

        var pos9 by remember {
            mutableStateOf("")
        }

        val canContinue by remember {
            derivedStateOf {
                pos3 == correctSelections[0] &&
                        pos7 == correctSelections[1] &&
                        pos9 == correctSelections[2]
            }
        }

        Text(
            text = stringResource(Res.string.name_your_account),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = stringResource(Res.string.select_the_correct_word_from_your_list),
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(25.dp))

        Text(
            text = "${stringResource(Res.string.position)} 3",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(10.dp))
        RandomWordSelector(
            words = listOf(correctSelections[0], fakeMnemonicPhrase[1], fakeMnemonicPhrase[2]),
            onWordSelected = { pos3 = it }
        )

        Spacer(Modifier.height(15.dp))
        Text(
            text = "${stringResource(Res.string.position)} 7",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(10.dp))
        RandomWordSelector(
            words = listOf(correctSelections[1], fakeMnemonicPhrase[3], fakeMnemonicPhrase[4]),
            onWordSelected = { pos7 = it }
        )

        Spacer(Modifier.height(15.dp))
        Text(
            text = "${stringResource(Res.string.position)} 9",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(10.dp))
        RandomWordSelector(
            words = listOf(correctSelections[2], fakeMnemonicPhrase[5], fakeMnemonicPhrase[6]),
            onWordSelected = { pos9 = it }
        )

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
                    onCompleted()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,// Color del botón
                    disabledContainerColor = Other.grey.copy(alpha = 0.6F)
                ),
                shape = RoundedCornerShape(50) // Bordes redondos al 50%
            ) {
                Text(
                    stringResource(Res.string.completed),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                ) // Texto del botón
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RandomWordSelector(
    words: List<String>,
    onWordSelected: (String) -> Unit
) {
    // Mezclar las palabras y mantener el estado
    val shuffledWords = remember(words) {
        words.toSet().toTypedArray().toList().shuffled()
    }
    var selectedWord by remember { mutableStateOf<String?>(null) }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        shuffledWords.forEach { word ->
            val isSelected = word == selectedWord
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) else Color.Transparent,
                animationSpec = tween(durationMillis = 200),
                label = "backgroundAnimation"
            )

            Box(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            selectedWord = word
                            onWordSelected(word)
                        },
                        indication = null, // Eliminar el efecto ripple
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = word,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
