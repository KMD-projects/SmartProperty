package com.myolwinoo.smartproperty

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myolwinoo.smartproperty.design.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginRoute
        ) {
            loginScreen(
                onLoginSuccess = {
                    navController.navigateToMain()
                }
            )

            mainScreen()
        }
    }
}