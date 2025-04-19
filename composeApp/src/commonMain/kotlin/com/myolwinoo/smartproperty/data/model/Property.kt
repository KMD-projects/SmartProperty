package com.myolwinoo.smartproperty.data.model

data class Property(
    val id: String,
    val landlordId: String,
    val title: String,
    val description: String,
    val price: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean,
    val propertyType: String,
    val amenities: List<String>,
    val images: List<String>,
    val available: Boolean = true,
    val appointmentStatus: AppointmentStatus?,
    val isOwnProperty: Boolean,
    val createdAt: String,
    val updatedAt: String
)

enum class PropertyType {
    APARTMENT, HOUSE, ROOM, CONDO, DORMITORY
}