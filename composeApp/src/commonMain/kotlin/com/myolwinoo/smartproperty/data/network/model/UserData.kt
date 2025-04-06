package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val gender: String? = null,
    val role: String? = null,
    @SerialName("profile_pic")
    val profilePic: String? = null,
    @SerialName("requisition_status")
    val requisitionStatus: String? = null,

    // during login, register
    @SerialName("access_token")
    val accessToken: String? = null,
)
