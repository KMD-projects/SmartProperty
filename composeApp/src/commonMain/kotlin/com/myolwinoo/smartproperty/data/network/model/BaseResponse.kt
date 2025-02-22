package com.myolwinoo.smartproperty.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Boolean,
    val message: String,
    val code: Int,
    val data: T
)
