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
    onSurface = Light.onSurface,
    background = Light.background,
    onBackground = Light.onBackground,
    tertiary = Light.tertiary,
    onTertiary = Light.onTertiary,
    inversePrimary = Light.inversePrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = Dark.primary,
    secondary = Dark.secondary,
    surface = Dark.surface,
    onSurface = Dark.onSurface,
    background = Dark.background,
    onBackground = Dark.onBackground,
    tertiary = Dark.tertiary,
    onTertiary = Dark.onTertiary,
    inversePrimary = Dark.inversePrimary
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