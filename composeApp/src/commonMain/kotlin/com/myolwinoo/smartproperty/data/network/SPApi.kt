package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.data.network.model.BaseResponse
import com.myolwinoo.smartproperty.data.network.model.RegisterRequest
import com.myolwinoo.smartproperty.data.network.model.UserData
import com.myolwinoo.smartproperty.utils.PreviewData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class SPApi(
    private val client: HttpClient,
    private val accountManager: AccountManager
) {
    suspend fun login(
        email: String,
        password: String
    ): Result<User> {
        return runCatching {
            val result = client.post("api/v1/login") {
                setBody(
                    mapOf(
                        "email" to email,
                        "password" to password
                    )
                )
            }.body<BaseResponse<UserData>>()

            result.data.accessToken?.let {
                accountManager.saveToken(it)
            }

            mapUser(result.data).also {
                accountManager.saveUser(it)
            }
        }
    }

    suspend fun register(
        request: RegisterRequest
    ): Result<User> {
        return runCatching {
            val result = client.post("api/v1/register") {
                setBody(request)
            }.body<BaseResponse<UserData>>()

            result.data.accessToken?.let {
                accountManager.saveToken(it)
            }

            mapUser(result.data).also {
                accountManager.saveUser(it)
            }
        }
    }

    suspend fun getProfile(): Result<User> {
        return runCatching {
            accountManager.getUser()
                ?.let { client.get("api/v1/users/${it.id}/profile") }
                ?.body<BaseResponse<UserData>>()
                ?.let { mapUser(it.data) }
                ?.also { accountManager.saveUser(it) }
                ?: throw IllegalStateException("User not found")
        }
    }

    suspend fun getPropertyList(): Result<List<Property>> {
        val properties = PreviewData.properties
        return Result.success(PreviewData.properties)
    }

    suspend fun getWishlists(): Result<List<Property>> {
        return getPropertyList()
            .map { it.filter { it.isFavorite } }
    }

//    suspend fun toggleFavorite(): Result<>

    private fun mapUser(userData: UserData): User {
        return User(
            id = userData.id.orEmpty(),
            name = userData.name.orEmpty(),
            email = userData.email.orEmpty(),
            phone = userData.phone.orEmpty(),
            address = userData.address.orEmpty(),
            role = when(userData.role.orEmpty()) {
                "renter" -> UserRole.RENTER
                "landlord" -> UserRole.LANDLORD
                else -> UserRole.RENTER
            },
            profileImage = userData.profilePic.orEmpty(),
            verified = false,
            createdAt = "",
            updatedAt = ""
        )
    }
}