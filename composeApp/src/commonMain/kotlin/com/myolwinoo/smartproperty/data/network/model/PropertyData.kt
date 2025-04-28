package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyData(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val address: String? = null,
    @SerialName("is_available")
    val isAvailable: Boolean? = null,
    @SerialName("property_type")
    val propertyType: Map<String, String?>? = null,
    @SerialName("is_favorite")
    val isFavorite: Boolean? = null,
    val landlord: Map<String, String?>? = null,
    val amenities: List<Map<String, String?>>? = null,
    val images: List<Map<String, String?>>? = null,
    @SerialName("last_appointment_status")
    val appointmentStatus: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("average_rating")
    val avgRating: Float? = null,
    @SerialName("view_count")
    val viewCount: Long? = null,
    @SerialName("has_reviewed")
    val hasReviewed: Boolean? = null,
    val reviews: List<RatingData>? = null
)
