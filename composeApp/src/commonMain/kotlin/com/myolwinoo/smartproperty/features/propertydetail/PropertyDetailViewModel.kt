package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.network.SPApi
import com.myolwinoo.smartproperty.data.network.model.RatingRequest
import io.github.aakira.napier.Napier
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

    var rating by mutableStateOf(0)
        private set

    var review: String? by mutableStateOf(null)

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
                .onSuccess {
                    _property.value = it
                    it.ownReview?.let { ownReview ->
                        rating = ownReview.rating.toInt()
                        review = ownReview.comment
                    }
                }
        }
    }

    fun setRatingValue(rating: Int) {
        this.rating = rating
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

    fun submitRating() {
        val reviewId = property.value?.ownReview?.id
        val request = RatingRequest(rating, review)
        viewModelScope.launch {
            if (reviewId == null) {
                spApi.submitRating(propertyId, request)
            } else {
                spApi.updateReview(propertyId, reviewId, request)
            }.onSuccess { refresh() }
                .onFailure {
                    Napier.i { it.message.toString() }
                }
        }
    }

    fun resetRating() {
        rating = property.value?.ownReview?.rating?.toInt() ?: 0
        review = property.value?.ownReview?.comment.orEmpty()
    }

    fun deleteReview() {
        val reviewId = property.value?.ownReview?.id ?: return
        viewModelScope.launch {
            spApi.deleteReview(propertyId, reviewId)
                .onSuccess { refresh() }
        }
    }
}