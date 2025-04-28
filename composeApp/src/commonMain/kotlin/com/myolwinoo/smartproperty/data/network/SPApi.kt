package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.AppConfiguration
import com.myolwinoo.smartproperty.data.AccountManager
import com.myolwinoo.smartproperty.data.model.Amenity
import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.data.model.PropertyType
import com.myolwinoo.smartproperty.data.model.Rating
import com.myolwinoo.smartproperty.data.model.RequisitionStatus
import com.myolwinoo.smartproperty.data.model.SearchRequest
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.data.network.model.AppointmentData
import com.myolwinoo.smartproperty.data.network.model.BaseResponse
import com.myolwinoo.smartproperty.data.network.model.CreateAppointmentRequest
import com.myolwinoo.smartproperty.data.network.model.CreatePropertyRequest
import com.myolwinoo.smartproperty.data.network.model.PropertyData
import com.myolwinoo.smartproperty.data.network.model.RatingData
import com.myolwinoo.smartproperty.data.network.model.RegisterRequest
import com.myolwinoo.smartproperty.data.network.model.UserData
import com.myolwinoo.smartproperty.utils.DateUtils
import com.myolwinoo.smartproperty.utils.PriceFormatter
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlin.text.get

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

    suspend fun createProperty(
        request: CreatePropertyRequest
    ): Result<Unit> {
        return runCatching {
            client.submitFormWithBinaryData(
                url = "api/v1/properties",
                formData = formData {

                    append("title", request.title.orEmpty())
                    append("description", request.description.orEmpty())
                    append("price", request.price.toString())
                    append("latitude", request.latitude.orEmpty())
                    append("longitude", request.longitude.orEmpty())
                    append("address", request.address.orEmpty())
                    append("property_type_id", request.propertyTypeId.orEmpty())

                    request.amenityIds?.forEach {
                        append("amenity_ids[]", it)
                    }

                    request.images?.forEachIndexed { index, image ->
                        append("images[]", image.data, Headers.build {
                            append(HttpHeaders.ContentType, "image/*")
                            append(HttpHeaders.ContentDisposition, "filename=${image.id}.jpg")
                        })
                    }
                }
            ) {
                onUpload { bytesSentTotal, contentLength ->
                    Napier.i("Uploading image: $bytesSentTotal/$contentLength")
                }
            }.body<BaseResponse<PropertyData>>()
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
                .data
                .let {

                }
        }
    }

    suspend fun getPropertyTypes(): Result<List<PropertyType>> {
        return runCatching {
            client.get("api/v1/property-types")
                .body<BaseResponse<List<Map<String, String?>>>>()
                .data
                .map {
                    PropertyType(
                        id = it["id"].orEmpty(),
                        name = it["name"].orEmpty()
                    )
                }
        }
    }

    suspend fun getAmenities(): Result<List<Amenity>> {
        return runCatching {
            client.get("api/v1/amenities")
                .body<BaseResponse<List<Map<String, String?>>>>()
                .data
                .map {
                    Amenity(
                        id = it["id"].orEmpty(),
                        name = it["name"].orEmpty()
                    )
                }
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
            images = propertyData.images?.map {
                val base = AppConfiguration.BASE_URL
                val baseDropped = base.dropLast(1)
                val url = it["url"].orEmpty()
                    .replace("http://smart-property.test/", base)
                PropertyImage.Remote(
                    id = it["id"].orEmpty(),
                    url = "${url}".also {
                        Napier.i("Image URL: $it")
                    }
                )
            }.orEmpty(),
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
            reviews = propertyData.reviews?.map { mapRatings(it) }.orEmpty()
        )
    }

    private suspend fun mapRatings(ratingData: RatingData): Rating{
        return Rating(
            id = ratingData.id.orEmpty(),
            rating = ratingData.rating ?: 0f,
            comment = ratingData.comment.orEmpty(),
            username = ratingData.reviewedBy?.get("username").orEmpty(),
            profilePic = ratingData.reviewedBy?.get("profile_pic").orEmpty(),
            isUserComment = ratingData.reviewedBy?.get("id") == accountManager.getUser()?.id,

        )
    }
}