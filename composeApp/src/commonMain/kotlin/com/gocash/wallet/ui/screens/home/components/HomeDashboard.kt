package com.gocash.wallet.ui.screens.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gocash.wallet.ui.shared.picture.Z17PictureAvatar
import com.gocash.wallet.ui.theme.Other
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.continue_label
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboard(
    modifier: Modifier = Modifier,
    accountName: String
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Z17PictureAvatar(
                        Modifier.size(40.dp),
                        source = null,
                        useTextPlaceholder = true,
                        textPlaceholder = accountName
                    )
                },
                actions = {
                    Button(
                        contentPadding = PaddingValues(vertical = 5.dp, horizontal = 10.dp),
                        onClick = {
                            // todo
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
            )
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboardBig(
    modifier: Modifier = Modifier,
    accountName: String
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Z17PictureAvatar(
                        Modifier.size(40.dp),
                        source = null,
                        useTextPlaceholder = true,
                        textPlaceholder = accountName
                    )
                },
                actions = {
                    Button(
                        contentPadding = PaddingValues(vertical = 5.dp, horizontal = 10.dp),
                        onClick = {
                            // todo
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
            )
        }
    ) {

    }
}