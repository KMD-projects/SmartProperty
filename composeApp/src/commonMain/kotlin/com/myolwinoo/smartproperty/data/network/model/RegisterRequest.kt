package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    @SerialName("password_confirmation")
    val confirmPassword: String,
    @SerialName("phone_number")
    val phone: String?,
    val address: String?,
)
