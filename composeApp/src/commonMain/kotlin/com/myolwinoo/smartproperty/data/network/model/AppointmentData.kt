package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentData(
    val id: String? = null,
    val user: Map<String, String?>? = null,
    @SerialName("request_to")
    val requestTo: Map<String, String?>? = null,
    val property: PropertyData? = null,
    val status: String? = null,
    val from: String? = null,
    val to: String? = null,
    val description: String? = null,
    val remark: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)