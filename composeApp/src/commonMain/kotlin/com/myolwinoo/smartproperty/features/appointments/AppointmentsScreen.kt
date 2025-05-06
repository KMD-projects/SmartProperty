@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.myolwinoo.smartproperty.features.appointments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.myolwinoo.smartproperty.common.EmptyView
import com.myolwinoo.smartproperty.common.appointmentList
import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.utils.ColumnHelper
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object AppointmentsRoute

@Composable
fun AppointmentsScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: AppointmentsViewModel = koinViewModel<AppointmentsViewModel>()
    val appointments = viewModel.appointments.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass,
        isLoading = viewModel.isLoading,
        appointments = appointments.value,
        onRefresh = viewModel::refresh,
        onAccept = viewModel::acceptAppointment,
        onDecline = viewModel::declineAppointment,
        onCancel = viewModel::cancelAppointment
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowWidthSizeClass,
    isLoading: Boolean,
    appointments: List<Appointment>,
    onRefresh: () -> Unit,
    onAccept: (String) -> Unit,
    onDecline: (String) -> Unit,
    onCancel: (String) -> Unit
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    val pullToRefreshState = rememberPullToRefreshState()
    val column = ColumnHelper.calculate(windowSizeClass)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = statusBarInset.calculateTopPadding())
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = AppDimens.Spacing.xl,
                vertical = 20.dp
            ),
            text = "Your Appointments",
            style = MaterialTheme.typography.headlineSmall
        )
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isLoading,
            onRefresh = onRefresh
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = AppDimens.Spacing.xl,
                    end = AppDimens.Spacing.xl,
                    bottom = 20.dp
                ),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                columns = StaggeredGridCells.Fixed(
                    count = column
                )
            ) {
                appointmentList(
                    appointments = appointments,
                    onAccept = onAccept,
                    onDecline = onDecline,
                    onCancel = onCancel
                )
            }

            if (appointments.isEmpty()) {
                EmptyView(
                    text = "No appointments yet."
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppointmentsScreen(

    )
}