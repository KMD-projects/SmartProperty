package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.utils.PreviewData
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.User
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

class SPApi(
    private val client: HttpClient
) {
    suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        delay(1000)
        return Result.success(PreviewData.user)
    }

    suspend fun register(
        email: String,
        password: String
    ): Result<User> {
        delay(1000)
        return Result.success(PreviewData.user)
    }

    suspend fun getPropertyList(): Result<List<Property>> {
        delay(1000)
        return Result.success(PreviewData.properties)
    }

    suspend fun getWishlists(): Result<List<Property>> {
        return getPropertyList()
            .map { it.filter { it.isFavorite } }
    }
}