package com.gocash.wallet.ui.screens.existingAccount.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.theme.Other
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.continue_label
import gowallet.composeapp.generated.resources.word
import org.jetbrains.compose.resources.stringResource

@Composable
fun EnterMnemonic(
    modifier: Modifier = Modifier,
    onSelected: (List<String>) -> Unit
) {
    WordList(
        modifier = modifier,
        count = 12,
        onCompleted = onSelected
    )
}

@Composable
fun Word(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit
) {
    var isWordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        visualTransformation = if (isWordVisible) {
            VisualTransformation.None // Muestra el texto si es visible
        } else {
            PasswordVisualTransformation() // Oculta el texto si no es visible
        },
        trailingIcon = {
            IconButton(onClick = { isWordVisible = !isWordVisible }) {
                Icon(
                    imageVector = if (isWordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isWordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent, // Fondo transparente al enfocar
            unfocusedContainerColor = Color.Transparent, // Fondo transparente al no enfocar
            focusedTextColor = MaterialTheme.colorScheme.onBackground,//- the color used for the input text of this text field when focused
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground, //- the color used for the input text of this text field when not focused
            focusedBorderColor = MaterialTheme.colorScheme.primary,//- the border color for this text field when focused
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,//- the border color for this text field when not focused
        )
    )
}

@Composable
fun WordList(
    modifier: Modifier = Modifier,
    count: Int,
    onCompleted: (List<String>) -> Unit
) {
    val listValues = remember { mutableStateListOf(*Array(count) { "" }) }
    val canContinue by remember(listValues) {
        derivedStateOf { listValues.none { it.isBlank() } } // Cambiado a none
    }

    Column(modifier) {
        repeat(count) { index ->
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "${stringResource(Res.string.word)} ${index + 1}",
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Word(
                modifier = Modifier.fillMaxWidth(),
                value = listValues[index],
                onChange = { listValues[index] = it }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = canContinue,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
            onClick = {
                onCompleted(listValues)
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

@Composable
fun EnterMnemonicBig(
    modifier: Modifier = Modifier,
    onSelected: (List<String>) -> Unit
) {
    WordListBig(
        modifier = modifier,
        count = 12,
        onCompleted = onSelected
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordListBig(
    modifier: Modifier = Modifier,
    count: Int,
    onCompleted: (List<String>) -> Unit
) {
    val listValues = remember { mutableStateListOf(*Array(count) { "" }) }
    val canContinue by remember(listValues) {
        derivedStateOf { listValues.none { it.isBlank() } } // Cambiado a none
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(Modifier, maxItemsInEachRow = 4) {
            repeat(count) { index ->
                WordBig(
                    modifier = Modifier.widthIn(0.dp, 200.dp).padding(10.dp),
                    value = listValues[index],
                    index = index,
                    onChange = { listValues[index] = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = canContinue,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
            onClick = {
                onCompleted(listValues)
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


@Composable
fun WordBig(
    modifier: Modifier = Modifier,
    value: String,
    index: Int,
    onChange: (String) -> Unit
) {
    var isWordVisible by remember { mutableStateOf(false) }

    Column(modifier) {
        Text(
            "${stringResource(Res.string.word)} ${index + 1}",
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            modifier = modifier,
            visualTransformation = if (isWordVisible) {
                VisualTransformation.None // Muestra el texto si es visible
            } else {
                PasswordVisualTransformation() // Oculta el texto si no es visible
            },
            trailingIcon = {
                IconButton(onClick = { isWordVisible = !isWordVisible }) {
                    Icon(
                        imageVector = if (isWordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isWordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, // Fondo transparente al enfocar
                unfocusedContainerColor = Color.Transparent, // Fondo transparente al no enfocar
                focusedTextColor = MaterialTheme.colorScheme.onBackground,//- the color used for the input text of this text field when focused
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground, //- the color used for the input text of this text field when not focused
                focusedBorderColor = MaterialTheme.colorScheme.primary,//- the border color for this text field when focused
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,//- the border color for this text field when not focused
            )
        )
    }
}
