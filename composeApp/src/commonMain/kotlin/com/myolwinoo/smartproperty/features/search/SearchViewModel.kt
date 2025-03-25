@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.myolwinoo.smartproperty.features.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.model.SearchRequest
import com.myolwinoo.smartproperty.data.network.SPApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

enum class PriceSorting(val rawValue: String) {
    ASC("asc"),
    DESC("desc")
}

class SearchViewModel(
    private val spApi: SPApi
): ViewModel() {

    var query by mutableStateOf(TextFieldValue())
        private set

    var minPrice by mutableStateOf(TextFieldValue())
        private set

    var maxPrice by mutableStateOf(TextFieldValue())
        private set

    var priceSorting by mutableStateOf<PriceSorting?>(null)
        private set

    private val minPriceFlow = snapshotFlow { minPrice }
    private val maxPriceFlow = snapshotFlow { maxPrice }
    private val priceSortingFlow = snapshotFlow { priceSorting }
    private val queryFlow = snapshotFlow { query.text }
        .debounce(300)
        .distinctUntilChanged()

    val hasFiltered = combine(
        minPriceFlow,
        maxPriceFlow,
        priceSortingFlow
    ) { minPrice, maxPrice, priceSorting ->
        minPrice.text.isNotBlank() || maxPrice.text.isNotBlank() || priceSorting != null
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = false
    )

    val searchResult = combine(
        minPriceFlow,
        maxPriceFlow,
        priceSortingFlow,
        queryFlow
    ) { minPrice, maxPrice, priceSorting, query ->
        SearchRequest(
            query = query,
            minPrice = minPrice.text.toLongOrNull(),
            maxPrice = maxPrice.text.toLongOrNull(),
            priceSorting = priceSorting
        )
    }
        .mapLatest {
            spApi.search(it).getOrNull().orEmpty()
        }
        .stateIn(
            viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun resetFilter() {
        minPrice = TextFieldValue()
        maxPrice = TextFieldValue()
        priceSorting = null
    }

    fun onQueryChange(q: TextFieldValue) {
        query = q
    }

    fun onMinPriceChange(p: TextFieldValue) {
        minPrice = p
    }

    fun onMaxPriceChange(p: TextFieldValue) {
        maxPrice = p
    }

    fun onPriceSortingChange(sorting: PriceSorting) {
        priceSorting = sorting
    }
}