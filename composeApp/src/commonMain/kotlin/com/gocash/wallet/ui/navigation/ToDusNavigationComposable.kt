package com.gocash.wallet.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlin.jvm.JvmSuppressWildcards

fun NavGraphBuilder.navigationComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(
            initialOffsetX = { 300 },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300))
    },
    exitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(
            targetOffsetX = { -300 },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    },
    popEnterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideInHorizontally(
            initialOffsetX = { -300 },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300))
    },
    popExitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutHorizontally(
            targetOffsetX = { 300 },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    this.composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        content
    )
}