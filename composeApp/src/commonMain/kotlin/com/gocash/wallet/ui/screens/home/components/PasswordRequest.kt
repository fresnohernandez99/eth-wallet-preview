package com.gocash.wallet.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.shared.picture.Z17BasePicture
import com.gocash.wallet.ui.theme.Other
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.forgot_my_password
import gowallet.composeapp.generated.resources.go_cash_banner
import gowallet.composeapp.generated.resources.insert_current_password
import gowallet.composeapp.generated.resources.lock
import gowallet.composeapp.generated.resources.password
import gowallet.composeapp.generated.resources.verify
import gowallet.composeapp.generated.resources.verifying
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordRequest(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    onCheckPassword: (String) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isEnabled by remember {
        derivedStateOf { password.isNotBlank() && password.length in 8..12 }
    }

    Column(modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center) {
        Z17BasePicture(
            modifier = Modifier.size(150.dp).align(alignment = Alignment.CenterHorizontally),
            source = Res.drawable.lock
        )

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(Res.string.verifying),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = stringResource(Res.string.insert_current_password),
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).clickable {
                // TODO GO FORGOT PASSWORD
            },
            text = stringResource(Res.string.forgot_my_password),
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(25.dp))

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


        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isEnabled,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 30.dp),
            onClick = {
                onCheckPassword(password)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,// Color del botón
                disabledContainerColor = Other.grey.copy(alpha = 0.6F)
            ),
            shape = RoundedCornerShape(50) // Bordes redondos al 50%
        ) {
            Text(
                stringResource(Res.string.verify),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            ) // Texto del botón
        }
    }
}