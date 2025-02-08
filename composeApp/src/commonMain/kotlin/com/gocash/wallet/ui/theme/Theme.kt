package com.gocash.wallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Light.primary,
    secondary = Light.secondary,
    surface = Light.surface,
    background = Light.background,
    onBackground = Light.onBackground
)

private val DarkColorScheme = darkColorScheme(
    primary = Dark.primary,
    secondary = Dark.secondary,
    surface = Dark.surface,
    background = Dark.background,
    onBackground = Dark.onBackground
)

@Composable
fun AppTheme(
    themeSelected: Int = 0,
    content: @Composable () -> Unit,
) {
    var isDarkTheme = false

    val colorScheme = when (themeSelected) {
        0 -> if (isSystemInDarkTheme()) {
            isDarkTheme = true
            DarkColorScheme
        } else {
            LightColorScheme
        }

        1 -> {
            LightColorScheme
        }

        2 -> {
            isDarkTheme = true
            DarkColorScheme
        }

        else -> if (isSystemInDarkTheme()) {
            isDarkTheme = true
            DarkColorScheme
        } else {
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(colorScheme),
        content = content
    )
}