package com.myolwinoo.smartproperty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object LoginRoute

fun NavController.navigateLogin() {
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable<LoginRoute> {
        Screen(
            onLoginSuccess = onLoginSuccess
        )
    }
}

@Composable
private fun Screen(
    onLoginSuccess: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Login Screen")
            Button(
                onClick = {
                    onLoginSuccess()
                }
            ) {
                Text("Login")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        onLoginSuccess = {}
    )
}