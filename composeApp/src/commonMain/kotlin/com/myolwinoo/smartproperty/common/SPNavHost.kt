package com.myolwinoo.smartproperty.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

private const val TRANSITION_DURATION = 300

private val enterTransition = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(durationMillis = TRANSITION_DURATION, easing = LinearOutSlowInEasing)
)

private val exitTransition = slideOutHorizontally(
    targetOffsetX = { -it / 3 },
    animationSpec = tween(durationMillis = TRANSITION_DURATION, easing = LinearOutSlowInEasing)
)

private val popEnterTransition = slideInHorizontally(
    initialOffsetX = { -it / 3 },
    animationSpec = tween(durationMillis = TRANSITION_DURATION, easing = LinearOutSlowInEasing)
)

private val popExitTransition = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(durationMillis = TRANSITION_DURATION, easing = LinearOutSlowInEasing)
)

@Composable
fun SPNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Any,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition },
        builder = builder
    )
}