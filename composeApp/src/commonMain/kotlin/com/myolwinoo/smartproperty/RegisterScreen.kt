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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_register

@Serializable
object RegisterRoute

fun NavController.navigateRegister() {
    navigate(RegisterRoute)
}

fun NavGraphBuilder.registerScreen(
    onRegisterSuccess: () -> Unit
) {
    composable<RegisterRoute> {
        Screen(
            onRegisterSuccess = onRegisterSuccess
        )
    }
}

@Composable
private fun Screen(
    onRegisterSuccess: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    onRegisterSuccess()
                }
            ) {
                Text(
                    text = stringResource(Res.string.label_register)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        onRegisterSuccess = {}
    )
}