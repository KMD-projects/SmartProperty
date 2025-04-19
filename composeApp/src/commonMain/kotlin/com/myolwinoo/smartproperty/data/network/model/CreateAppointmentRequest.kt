package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAppointmentRequest(
    @SerialName("property_id")
    val propertyId: String,
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String,
    @SerialName("description")
    val description: String
)
