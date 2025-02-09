package com.gocash.wallet.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.password
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordRequest(modifier: Modifier = Modifier) {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isEnabled by remember {
        derivedStateOf { password.isNotBlank() && password.length in 8..12 }
    }

    Column(modifier) {
        Text(
            stringResource(Res.string.password),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None // Muestra el texto si es visible
            } else {
                PasswordVisualTransformation() // Oculta el texto si no es visible
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
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

        Spacer(modifier = Modifier.height(30.dp))
    }
}