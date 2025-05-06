package com.myolwinoo.smartproperty.features.propertydetail.appointmentform

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.network.SPApi
import com.myolwinoo.smartproperty.data.network.model.CreateAppointmentRequest
import com.myolwinoo.smartproperty.utils.DateUtils
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppointmentFormViewModel(
    private val propertyId: String,
    private val spApi: SPApi
): ViewModel() {

    var fromDate by mutableStateOf(TextFieldValue())
        private set
    var fromTime by mutableStateOf(TextFieldValue())
        private set
    var toDate by mutableStateOf(TextFieldValue())
        private set
    var toTime by mutableStateOf(TextFieldValue())
        private set
    var description by mutableStateOf(TextFieldValue())
        private set

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    val isButtonEnabled: StateFlow<Boolean> = combine(
        snapshotFlow { fromDate.text },
        snapshotFlow { fromTime.text },
        snapshotFlow { toDate.text },
        snapshotFlow { toTime.text },
        snapshotFlow { description.text },
    ) { fromDate, fromTime, toDate, toTime, desc ->
        fromDate.isNotBlank()
                && fromTime.isNotBlank()
                && toDate.isNotBlank()
                && toTime.isNotBlank()
                && desc.isNotBlank()
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

    fun onFromDateChange(value: TextFieldValue) {
        fromDate = value
    }

    fun onToDateChange(value: TextFieldValue) {
        toDate = value
    }

    fun onFromTimeChange(value: TextFieldValue) {
        fromTime = value
    }

    fun onToTimeChange(value: TextFieldValue) {
        toTime = value
    }

    fun onDescriptionChange(value: TextFieldValue) {
        description = value
    }

    fun makeAppointment() {
        viewModelScope.launch {
            spApi.makeAppointment(
                request = CreateAppointmentRequest(
                    propertyId = propertyId,
                    from = DateUtils.toServerDate("${fromDate.text} ${fromTime.text}"),
                    to = DateUtils.toServerDate("${toDate.text} ${toTime.text}"),
                    description = description.text
                )
            ).onFailure {
                Napier.e { it.message.orEmpty() }
            }.onSuccess {
                _events.emit("success")
            }
        }
    }
}