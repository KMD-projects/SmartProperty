package com.myolwinoo.smartproperty.features.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val spApi: SPApi
) : ViewModel() {

    private val _properties = MutableStateFlow<List<Property>>(emptyList())
    val properties: StateFlow<List<Property>> = _properties

    var isLoading by mutableStateOf(false)
        private set

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            spApi.getPropertyList()
                .onSuccess { result ->
                    _properties.update { result }
                }
            isLoading = false
        }
    }

    fun toggleFavorite(propertyId: String) {
        viewModelScope.launch {
            spApi.toggleFavorite(propertyId)
                .onSuccess {
                    _properties.update { list ->
                        list.map {
                            if (it.id == propertyId) {
                                it.copy(isFavorite = !it.isFavorite)
                            } else {
                                it
                            }
                        }
                    }
                }
        }
    }
}