package com.myolwinoo.smartproperty

import androidx.compose.runtime.Composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.di.dataModule
import com.myolwinoo.smartproperty.di.viewModelModule
import com.myolwinoo.smartproperty.features.login.LoginRoute
import com.myolwinoo.smartproperty.features.login.loginScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                viewModelModule,
                dataModule
            )
        }
    ) {
        SPTheme {
            AppNavHost()
        }
    }
}

@Serializable
data object AuthRoute

@Composable
private fun AppNavHost() {
    val navController = rememberNavController()
    SPNavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        navigation<AuthRoute>(
            startDestination = LoginRoute
        ) {
            val navigateToMain = {
                navController.navigate(MainRoute) {
                    popUpTo(AuthRoute) {
                        inclusive = true
                    }
                }
            }
            loginScreen(
                onLoginSuccess = navigateToMain,
                onRegisterClick = navController::navigateRegister
            )

            registerScreen(
                onRegisterSuccess = navigateToMain
            )
        }

        mainScreen()
    }
}