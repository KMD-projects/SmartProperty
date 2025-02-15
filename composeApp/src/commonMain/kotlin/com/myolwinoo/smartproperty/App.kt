package com.myolwinoo.smartproperty

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myolwinoo.smartproperty.common.SPNavHost
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.di.dataModule
import com.myolwinoo.smartproperty.di.viewModelModule
import com.myolwinoo.smartproperty.features.login.LoginRoute
import com.myolwinoo.smartproperty.features.login.loginScreen
import com.myolwinoo.smartproperty.features.main.MainRoute
import com.myolwinoo.smartproperty.features.main.mainScreen
import com.myolwinoo.smartproperty.features.register.navigateRegister
import com.myolwinoo.smartproperty.features.register.registerScreen
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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navigateToMain = {
        navController.navigate(MainRoute) {
            popUpTo(AuthNavRoute) {
                inclusive = true
            }
        }
    }

    SPNavHost(
        navController = navController,
        startDestination = AuthNavRoute
    ) {
        authNav(
            navController = navController,
            onAuthSuccess = navigateToMain
        )

        mainScreen(
            onLogout = {
                navController.navigate(AuthNavRoute) {
                    popUpTo(MainRoute) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Serializable
data object AuthNavRoute

fun NavGraphBuilder.authNav(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    navigation<AuthNavRoute>(
        startDestination = LoginRoute
    ) {
        loginScreen(
            onLoginSuccess = onAuthSuccess,
            onRegisterClick = navController::navigateRegister
        )

        registerScreen(
            onRegisterSuccess = onAuthSuccess,
            onLogin = navController::popBackStack
        )
    }
}