package com.myolwinoo.smartproperty.features.appointments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppointmentsViewModel(
    private val spApi: SPApi,
    private val accountManager: AccountManager
): ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    var isLoading by mutableStateOf(false)
        private set

    val userRole = accountManager.userFlow
        .map { it?.role }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            spApi.getAppointments()
                .onSuccess { result ->
                    _appointments.update { result }
                }
            isLoading = false
        }
    }

    fun cancelAppointment(appointmentId: String) {
        updateStatus(appointmentId, AppointmentStatus.CANCELLED)
    }

    fun acceptAppointment(appointmentId: String) {
        updateStatus(appointmentId, AppointmentStatus.ACCEPTED)
    }

    fun declineAppointment(appointmentId: String) {
        updateStatus(appointmentId, AppointmentStatus.REJECTED)
    }

    fun updateStatus(appointmentId: String, status: AppointmentStatus) {
        viewModelScope.launch {
            spApi.updateAppointmentStatus(appointmentId, status)
                .onSuccess {
                    _appointments.update {
                        it.map {
                            if (it.id == appointmentId) {
                                it.copy(
                                    status = status,
                                    action = when {
                                        status == AppointmentStatus.PENDING && userRole.value == UserRole.LANDLORD ->
                                            AppointmentAction.ACCEPT_REJECT
                                        status == AppointmentStatus.PENDING ->
                                            AppointmentAction.CANCEL
                                        else -> AppointmentAction.NONE
                                    }
                                )
                            } else {
                                it
                            }
                        }
                    }
                }
        }
    }
}