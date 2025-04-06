package com.myolwinoo.smartproperty.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val role: UserRole,
    val requisitionStatus: RequisitionStatus?,
    val profileImage: String?,
    val verified: Boolean,
    val createdAt: String,
    val updatedAt: String
)