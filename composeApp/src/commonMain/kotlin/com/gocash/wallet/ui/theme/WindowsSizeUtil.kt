package com.gocash.wallet.ui.theme

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

// Copyright 2022 Google LLC.
// SPDX-License-Identifier: Apache-2.0

enum class WindowSize { Compact, Medium, Expanded }

/**
 * Remembers the [WindowSize] class for the window corresponding to the
 * current window metrics.
 */
@Composable
fun rememberWindowSizeClass(): WindowSize {
    var windowDpSize by remember {
        mutableStateOf(0.dp)
    }
    BoxWithConstraints(modifier = Modifier.padding(16.dp)) {
        windowDpSize = this.maxWidth
    }
    // Calculate the window size class
    return getWindowSizeClass(windowDpSize)
}

/**
 * Partitions a [DpSize] into an enumerated [WindowSize] class.
 */
fun getWindowSizeClass(windowDpSize: Dp): WindowSize = when {
    windowDpSize < 0.dp ->
        throw IllegalArgumentException("Dp value cannot be negative")

    windowDpSize < 600.dp -> WindowSize.Compact
    windowDpSize < 840.dp -> WindowSize.Medium
    else -> WindowSize.Expanded
}