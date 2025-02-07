package com.myolwinoo.smartproperty.data.model

data class Review(
    val id: String = "",
    val propertyId: String = "",
    val renterId: String = "",
    val rating: Float = 0f,
    val comment: String? = null,
    val createdAt: String
)