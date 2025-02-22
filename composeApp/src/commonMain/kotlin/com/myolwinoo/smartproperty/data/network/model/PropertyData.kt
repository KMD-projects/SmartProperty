package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyData(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: String? = null,
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
    val images: List<String>? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)
