package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingData(
    val id: String? = null,
    val rating: Float? = null,
    val comment: String? = null,
    @SerialName("reviewed_by")
    val reviewedBy: Map<String, String?>?,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)
