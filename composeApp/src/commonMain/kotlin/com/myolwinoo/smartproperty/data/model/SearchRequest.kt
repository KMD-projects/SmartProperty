package com.myolwinoo.smartproperty.data.model

import com.myolwinoo.smartproperty.features.search.PriceSorting

data class SearchRequest(
    val query: String,
    val minPrice: Long?,
    val maxPrice: Long?,
    val priceSorting: PriceSorting?
)
