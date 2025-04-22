package com.myolwinoo.smartproperty

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myolwinoo.smartproperty.common.SPNavHost
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.di.appModule
import com.myolwinoo.smartproperty.features.login.LoginRoute
import com.myolwinoo.smartproperty.features.login.loginScreen
import com.myolwinoo.smartproperty.features.main.MainRoute
import com.myolwinoo.smartproperty.features.main.mainScreen
import com.myolwinoo.smartproperty.features.propertydetail.appointmentform.appointmentForm
import com.myolwinoo.smartproperty.features.propertydetail.appointmentform.navigateAppointmentForm
import com.myolwinoo.smartproperty.features.propertydetail.navigatePropertyDetailScreen
import com.myolwinoo.smartproperty.features.propertydetail.propertyDetailScreen
import com.myolwinoo.smartproperty.features.propertyform.navigatePropertyForm
import com.myolwinoo.smartproperty.features.propertyform.propertyForm
import com.myolwinoo.smartproperty.features.register.navigateRegister
import com.myolwinoo.smartproperty.features.register.registerScreen
import com.myolwinoo.smartproperty.features.search.navigateSearch
import com.myolwinoo.smartproperty.features.search.searchScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                appModule
            )
        }
    ) {
        SPTheme {
            AppNavHost()
        }
    }
}

@Composable
@Preview
fun AppWithoutKoin() {
    SPTheme {
        AppNavHost()
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val accountManager = koinInject<AccountManager>()

    val isLoggedIn = accountManager.isLoggedIn()

    val navigateToMain = {
        navController.navigate(MainRoute) {
            popUpTo(AuthNavRoute) {
                inclusive = true
            }
        }
    }

    SPNavHost(
        navController = navController,
        startDestination = if (isLoggedIn) {
            MainRoute
        } else {
            AuthNavRoute
        }
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
            },
            navigateToSearch = navController::navigateSearch,
            navigateToPropertyDetail = navController::navigatePropertyDetailScreen,
            onCreateProperty = {
                navController.navigatePropertyForm(null)
            }
        )

        searchScreen(
            onBack = navController::navigateUp,
            navigateToPropertyDetail = navController::navigatePropertyDetailScreen
        )

        propertyDetailScreen(
            onBack = navController::navigateUp,
            navigateToAppointmentForm = navController::navigateAppointmentForm
        )

        appointmentForm(
            onBack = navController::navigateUp
        )

        propertyForm(
            onBack = navController::navigateUp
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