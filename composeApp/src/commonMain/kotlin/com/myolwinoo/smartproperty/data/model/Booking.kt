package com.myolwinoo.smartproperty.data.model

data class Booking(
    val id: String = "",
    val renterId: String = "",
    val propertyId: String = "",
    val landlordId: String = "",
    val startDate: Long = 0L,
    val endDate: Long = 0L,
    val status: BookingStatus = BookingStatus.PENDING,
    val totalPrice: Double = 0.0,
    val createdAt: String
)

enum class BookingStatus {
    PENDING, CONFIRMED, CANCELED, COMPLETED
}