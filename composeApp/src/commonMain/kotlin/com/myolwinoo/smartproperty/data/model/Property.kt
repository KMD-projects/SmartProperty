package com.myolwinoo.smartproperty.data.model

data class Property(
    val id: String,
    val landlordId: String,
    val title: String,
    val description: String,
    val price: Double,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean,
    val propertyType: PropertyType,
    val amenities: List<String>,
    val images: List<String>,
    val available: Boolean = true,
    val createdAt: String,
    val updatedAt: String
)

enum class PropertyType {
    APARTMENT, HOUSE, ROOM, CONDO, DORMITORY
}