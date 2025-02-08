package com.gocash.wallet.ui.screens.register.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StepProgress(
    modifier: Modifier = Modifier,
    currentStep: Int
) {
    val steps = listOf(0, 1, 2, 3)
    val progressColor = MaterialTheme.colorScheme.secondary
    val stepSize = 48.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, step ->
            StepNumber(
                number = (step + 1),
                isActive = currentStep >= step,
                size = stepSize
            )

            if (index < steps.lastIndex) {
                StepProgressBar(
                    currentStep = currentStep,
                    targetStep = index,
                    color = progressColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StepNumber(
    number: Int,
    isActive: Boolean,
    size: Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(
                color = if (isActive) MaterialTheme.colorScheme.secondary else Color.Gray,
                shape = CircleShape
            )
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun StepProgressBar(
    currentStep: Int,
    targetStep: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentStep > targetStep) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        label = "progress_animation"
    )

    Canvas(modifier = modifier.height(4.dp)) {
        // Fondo de la barra
        drawRect(
            color = Color.LightGray,
            size = Size(size.width, size.height)
        )

        // Parte animada
        drawRect(
            color = color,
            size = Size(size.width * animatedProgress, size.height)
        )
    }
}
