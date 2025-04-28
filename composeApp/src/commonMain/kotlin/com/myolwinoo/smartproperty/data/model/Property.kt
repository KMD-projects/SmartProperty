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
    val images: List<PropertyImage>,
    val available: Boolean = true,
    val viewcount: Long,
    val avgRating: String,
    val appointmentStatus: AppointmentStatus?,
    val isOwnProperty: Boolean,
    val hasReviewed: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val reviews: List<Rating>
) {
    val firstImage = images.firstOrNull()
        ?.let { it as? PropertyImage.Remote }
        ?.url

    val ownReview = reviews.firstOrNull { it.isUserComment }

    val topReviews = reviews
        .filterNot { it.isUserComment }
        .sortedByDescending { it.rating }
        .take(if (ownReview != null) 2 else 3)
}