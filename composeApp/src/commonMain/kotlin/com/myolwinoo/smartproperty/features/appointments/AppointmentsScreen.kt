package com.myolwinoo.smartproperty.features.appointments

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object AppointmentsRoute

@Composable
fun AppointmentsScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        "Appointments Screen"
    )
}

@Preview
@Composable
private fun Preview() {
    AppointmentsScreen(

    )
}