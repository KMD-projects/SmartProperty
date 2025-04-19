package com.myolwinoo.smartproperty.features.propertydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class PropertyDetailViewModel(
    private val propertyId: String,
    private val spApi: SPApi,
    accountManager: AccountManager,
): ViewModel() {

    private val _property = MutableStateFlow<Property?>(null)
    val property: StateFlow<Property?> = _property

    val userRole = accountManager.userFlow
        .map { it?.role }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            spApi.getProperty(propertyId)
                .onSuccess { _property.value = it }
        }
    }

    fun toggleFavorite(propertyId: String) {
        viewModelScope.launch {
            spApi.toggleFavorite(propertyId)
                .onSuccess {
                    _property.update { data ->
                        data?.copy(isFavorite = !data.isFavorite)
                    }
                }
        }
    }

    fun updateStatus(appointmentId: String) {
        viewModelScope.launch {
            _property.update {
                it?.copy(appointmentStatus = null)
            }
        }
    }
}