package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.RequisitionStatus
import com.myolwinoo.smartproperty.data.model.SearchRequest
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.data.network.model.AppointmentData
import com.myolwinoo.smartproperty.data.network.model.BaseResponse
import com.myolwinoo.smartproperty.data.network.model.CreateAppointmentRequest
import com.myolwinoo.smartproperty.data.network.model.PropertyData
import com.myolwinoo.smartproperty.data.network.model.RatingData
import com.myolwinoo.smartproperty.data.network.model.RegisterRequest
import com.myolwinoo.smartproperty.data.network.model.UserData
import com.myolwinoo.smartproperty.utils.DateUtils
import com.myolwinoo.smartproperty.utils.PriceFormatter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.parameters

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

    suspend fun logout(): Result<Unit> {
        return runCatching {
//            client.post("api/v1/logout")
            accountManager.logout()
        }
    }

    suspend fun becomeLandlord(): Result<Unit> {
        return runCatching {
            client.post("api/v1/landlord-requests")
        }.map { loadProfile() }
    }

    suspend fun loadProfile(): Result<User> {
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
        return runCatching {
            client.get("api/v1/properties")
                .body<BaseResponse<List<PropertyData>>>()
                .data
                .map { mapProperty(it) }
        }
    }

    suspend fun getProperty(id: String): Result<Property> {
        return runCatching {
            client.get("api/v1/properties/$id")
                .body<BaseResponse<PropertyData>>()
                .data
                .let { mapProperty(it) }
        }
    }

    suspend fun getAppointments(
        role: UserRole? = null,
        status: AppointmentStatus? = null,
        propertyId: String? = null
    ): Result<List<Appointment>> {
        val viewAs = role ?: accountManager.getUser()?.role
        return runCatching {
            client.get("api/v1/appointments") {
                url {
                    propertyId?.let { parameters.append("property_id", it) }
                    status?.let { parameters.append("status", it.rawValue) }
                    viewAs?.let { parameters.append("as", it.rawValue) }
                }
            }
                .body<BaseResponse<List<AppointmentData>>>()
                .data
                .map { mapAppointment(it) }
        }
    }

    suspend fun updateAppointmentStatus(
        appointmentId: String,
        status: AppointmentStatus
    ): Result<Unit> {
        return runCatching {
            client.put("api/v1/appointments/$appointmentId") {
                url {
                    parameters.append("status", status.rawValue)
                }
            }
                .body<BaseResponse<AppointmentData>>()
                .data
                .let { mapAppointment(it) }
        }
    }

    suspend fun search(request: SearchRequest): Result<List<Property>> {
        return runCatching {
            client.get("api/v1/properties") {
                url {
                    parameters.append("address", request.query)
                    request.minPrice?.let {
                        parameters.append("min_price", it.toString())
                    }
                    request.maxPrice?.let {
                        parameters.append("max_price", it.toString())
                    }
                    request.priceSorting?.let {
                        parameters.append("order", "price")
                        parameters.append("order_by", it.rawValue)
                    }
                }
            }
                .body<BaseResponse<List<PropertyData>>>()
                .data
                .map { mapProperty(it) }
        }
    }

    suspend fun getWishlists(): Result<List<Property>> {
        return runCatching {
            client.get("api/v1/properties") {
                url {
                    parameters.append("filter", "favorite")
                }
            }
                .body<BaseResponse<List<PropertyData>>>()
                .data
                .map { mapProperty(it) }
        }
    }

    suspend fun toggleFavorite(propertyId: String): Result<Unit> {
        return runCatching {
            client.post("api/v1/properties/$propertyId/favorite")
                .body<BaseResponse<List<Unit>>>()
        }
    }

    suspend fun makeAppointment(request: CreateAppointmentRequest): Result<Unit> {
        return runCatching {
            client.post("api/v1/appointments") {
                setBody(request)
            }.body<BaseResponse<AppointmentData>>()
        }
    }

    suspend fun submitRating(propertyId: String, rating: Int): Result<Unit> {
        return runCatching {
            client.post("api/v1/properties/$propertyId/reviews") {
                setBody(mapOf("rating" to rating))
            }.body<BaseResponse<RatingData>>()
        }
    }

    private fun mapUser(userData: UserData): User {
        return User(
            id = userData.id.orEmpty(),
            name = userData.name.orEmpty(),
            email = userData.email.orEmpty(),
            phone = userData.phone.orEmpty(),
            address = userData.address.orEmpty(),
            role = UserRole.fromRawValue(userData.role),
            requisitionStatus = RequisitionStatus
                .fromRawValue(userData.requisitionStatus),
            profileImage = userData.profilePic.orEmpty(),
            verified = false,
            createdAt = "",
            updatedAt = ""
        )
    }

    private suspend fun mapAppointment(data: AppointmentData): Appointment {
        val userRole = accountManager.getUser()?.role ?: UserRole.RENTER
        val status = AppointmentStatus.fromRawValue(data.status)
        return Appointment(
            id = data.id.orEmpty(),
            property = mapProperty(data.property!!),
            status = status,
            fromDate = DateUtils.toDisplayDate(data.from.orEmpty()),
            toDate = DateUtils.toDisplayDate(data.to.orEmpty()),
            description = data.description.orEmpty(),
            remark = data.remark.orEmpty(),
            renterName = data.user?.get("username").orEmpty(),
            renterProfileUrl = data.user?.get("profile_pic").orEmpty(),
            landlordName = data.requestTo?.get("username").orEmpty(),
            landlordProfileUrl = data.requestTo?.get("profile_pic").orEmpty(),
            action = when {
                status == AppointmentStatus.PENDING && userRole == UserRole.LANDLORD ->
                    AppointmentAction.ACCEPT_REJECT
                status == AppointmentStatus.PENDING ->
                    AppointmentAction.CANCEL
                else -> AppointmentAction.NONE
            }
        )
    }

    private suspend fun mapProperty(propertyData: PropertyData): Property {
        val landlordId = propertyData.landlord?.get("id").orEmpty()
        return Property(
            id = propertyData.id.orEmpty(),
            landlordId = landlordId,
            title = propertyData.title.orEmpty(),
            description = propertyData.description.orEmpty(),
            price = PriceFormatter.format(propertyData.price ?: 0.0),
            location = propertyData.address.orEmpty(),
            amenities = propertyData.amenities?.map { it["name"].orEmpty() }.orEmpty(),
            // todo: update images here
            images = listOf(
                "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=2070",
                "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?q=80&w=2000",
                "https://images.unsplash.com/photo-1613490493576-7fde63acd811?q=80&w=2000",
                "https://plus.unsplash.com/premium_photo-1661874933205-969c5bfa3523?q=80&w=2128",
                "https://images.unsplash.com/photo-1613490493576-7fde63acd811?q=80&w=2000",
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=2000"
            ).shuffled(),
            available = propertyData.isAvailable ?: false,
            latitude = propertyData.latitude?.toDoubleOrNull() ?: 0.0,
            longitude = propertyData.longitude?.toDoubleOrNull() ?: 0.0,
            propertyType = propertyData.propertyType?.get("name").orEmpty(),
            createdAt = propertyData.createdAt.orEmpty(),
            updatedAt = propertyData.updatedAt.orEmpty(),
            isFavorite = propertyData.isFavorite ?: false,
            appointmentStatus = AppointmentStatus
                .fromRawValue(propertyData.appointmentStatus),
            isOwnProperty = landlordId == accountManager.getUser()?.id,
            avgRating = propertyData.avgRating ?: 0f,
            viewcount = propertyData.viewCount ?: 0,
            hasReviewed = propertyData.hasReviewed ?: false,
        )
    }
}