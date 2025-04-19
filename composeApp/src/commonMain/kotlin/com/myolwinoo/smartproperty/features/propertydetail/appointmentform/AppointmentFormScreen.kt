@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.propertydetail.appointmentform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.myolwinoo.smartproperty.common.DatePickerInput
import com.myolwinoo.smartproperty.common.DateTimePicker
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_back
import smartproperty.composeapp.generated.resources.label_book_appointment
import smartproperty.composeapp.generated.resources.label_date
import smartproperty.composeapp.generated.resources.title_make_appointment
import smartproperty.composeapp.generated.resources.label_from
import smartproperty.composeapp.generated.resources.label_time
import smartproperty.composeapp.generated.resources.label_to
import smartproperty.composeapp.generated.resources.title_welcome

@Serializable
private data class AppointmentFormRoute(val propertyId: String)

fun NavController.navigateAppointmentForm(
    propertyId: String
) {
    navigate(AppointmentFormRoute(propertyId))
}

fun NavGraphBuilder.appointmentForm(
    onBack: () -> Unit,
) {
    composable<AppointmentFormRoute> {
        val propertyId = it.toRoute<AppointmentFormRoute>().propertyId
        val viewModel: AppointmentFormViewModel = koinViewModel {
            parametersOf(propertyId)
        }

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    "success" -> onBack()
                }
            }
        }

        Screen(
            onBack = onBack,
            fromDate = viewModel.fromDate,
            onFromDateChange = viewModel::onFromDateChange,
            toDate = viewModel.toDate,
            onToDateChange = viewModel::onToDateChange,
            description = viewModel.description,
            onDescriptionChange = viewModel::onDescriptionChange,
            fromTime = viewModel.fromTime,
            onFromTimeChange = viewModel::onFromTimeChange,
            toTime = viewModel.toTime,
            onToTimeChange = viewModel::onToTimeChange,
            onSubmit = viewModel::makeAppointment
        )
    }
}

@Composable
private fun Screen(
    onBack: () -> Unit,
    fromDate: TextFieldValue,
    onFromDateChange: (TextFieldValue) -> Unit,
    fromTime: TextFieldValue,
    onFromTimeChange: (TextFieldValue) -> Unit,
    toDate: TextFieldValue,
    onToDateChange: (TextFieldValue) -> Unit,
    toTime: TextFieldValue,
    onToTimeChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    onSubmit: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.title_make_appointment),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                text = stringResource(Res.string.label_from),
                style = MaterialTheme.typography.titleMedium
            )
            DateTimePicker(
                modifier = Modifier.padding(horizontal = AppDimens.Spacing.xl),
                dateLabel = stringResource(Res.string.label_date),
                date = fromDate,
                onDateChange = onFromDateChange,
                isDateError = false,
                timeLabel = stringResource(Res.string.label_time),
                time = fromTime,
                onTimeChange = onFromTimeChange,
                isTimeError = false
            )
            Spacer(modifier = Modifier.height(AppDimens.Spacing.l))
            Text(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                text = stringResource(Res.string.label_to),
                style = MaterialTheme.typography.titleMedium
            )
            DateTimePicker(
                modifier = Modifier.padding(horizontal = AppDimens.Spacing.xl),
                dateLabel = stringResource(Res.string.label_date),
                date = toDate,
                onDateChange = onToDateChange,
                isDateError = false,
                timeLabel = stringResource(Res.string.label_time),
                time = toTime,
                onTimeChange = onToTimeChange,
                isTimeError = false
            )
            Spacer(modifier = Modifier.height(AppDimens.Spacing.l))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                minLines = 4
            )
            Spacer(modifier = Modifier.height(AppDimens.Spacing.xl))
            Button(
                modifier = Modifier
                    .padding(
                        start = AppDimens.Spacing.xl,
                        end = AppDimens.Spacing.xl,
                        bottom = AppDimens.Spacing.xxl
                    )
                    .widthIn(max = AppDimens.maxWidth)
                    .fillMaxWidth(),
                onClick = { onSubmit() }
            ) {
                Text(
                    text = stringResource(Res.string.label_book_appointment)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            onBack = {},
            fromDate = TextFieldValue(),
            onFromDateChange = {},
            toDate = TextFieldValue(),
            onToDateChange = {},
            description = TextFieldValue(),
            onDescriptionChange = {},
            fromTime = TextFieldValue(),
            onFromTimeChange = {},
            toTime = TextFieldValue(),
            onToTimeChange = {},
            onSubmit = {}
        )
    }
}