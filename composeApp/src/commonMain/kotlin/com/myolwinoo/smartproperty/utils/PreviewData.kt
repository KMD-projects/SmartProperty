package com.myolwinoo.smartproperty.utils

import com.myolwinoo.smartproperty.data.model.Appointment
import com.myolwinoo.smartproperty.data.model.AppointmentAction
import com.myolwinoo.smartproperty.data.model.AppointmentStatus
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.PropertyType
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole

object PreviewData {

    val testEmail = "myo@test.com"
    val testPassword = "password"

    val user = User(
        id = "123",
        name = "Emrys",
        email = "emrys@gmail.com",
        phone = "09123456789",
        address = "test",
        role = UserRole.RENTER,
        requisitionStatus = null,
        profileImage = "",
        verified = true,
        createdAt = "",
        updatedAt = "",
    )

    val properties = listOf(
        Property(
            id = "1",
            landlordId = "123",
            title = "Cozy Studio Apartment With Good Facilities",
            description = "Fully furnished studio with WiFi & AC.",
            price = "500,000",
            location = "Yangon, Myanmar",
            amenities = listOf("WiFi", "AC", "Parking"),
            images = listOf(
                "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=2070",
            ),
            available = true,
            latitude = 16.8409,
            longitude = 96.1735,
            propertyType = PropertyType.APARTMENT.name,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-01",
            appointmentStatus = null,
            isFavorite = false,
            isOwnProperty = false
        ),
        Property(
            id = "2",
            landlordId = "456",
            title = "Modern Condo with City View",
            description = "Spacious condo with a stunning view of downtown Yangon.",
            price = "1,200,000",
            location = "Downtown, Yangon",
            amenities = listOf("Gym", "Swimming Pool", "Security"),
            images = listOf(
                "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?q=80&w=2000"
            ),
            available = true,
            latitude = 16.7742,
            longitude = 96.1584,
            propertyType = PropertyType.CONDO.name,
            createdAt = "2023-02-15",
            updatedAt = "2023-02-15",
            appointmentStatus = null,
            isFavorite = true,
            isOwnProperty = false
        ),
        Property(
            id = "3",
            landlordId = "789",
            title = "Luxury Villa with Private Pool",
            description = "A stunning villa with a private pool and garden area.",
            price = "5,000,000",
            location = "Inya Road, Yangon",
            amenities = listOf("Private Pool", "Garden", "Security", "Garage"),
            images = listOf(
                "https://images.unsplash.com/photo-1613490493576-7fde63acd811?q=80&w=2000"
            ),
            available = false,
            latitude = 16.8133,
            longitude = 96.1566,
            propertyType = PropertyType.HOUSE.name,
            createdAt = "2023-03-10",
            updatedAt = "2023-03-10",
            appointmentStatus = null,
            isFavorite = false,
            isOwnProperty = false
        ),
        Property(
            id = "4",
            landlordId = "159",
            title = "Affordable Room in Shared House",
            description = "A budget-friendly private room in a shared house.",
            price = "250,000",
            location = "Sanchaung, Yangon",
            amenities = listOf("Shared Kitchen", "WiFi", "Laundry"),
            images = listOf(
                "https://plus.unsplash.com/premium_photo-1661874933205-969c5bfa3523?q=80&w=2128"
            ),
            available = true,
            latitude = 16.7991,
            longitude = 96.1441,
            propertyType = PropertyType.ROOM.name,
            createdAt = "2023-04-05",
            updatedAt = "2023-04-05",
            appointmentStatus = null,
            isFavorite = true,
            isOwnProperty = false
        ),
        Property(
            id = "5",
            landlordId = "753",
            title = "Furnished Serviced Apartment",
            description = "Includes housekeeping, free WiFi, and breakfast.",
            price = "2,000,000",
            location = "Bahan, Yangon",
            amenities = listOf("Housekeeping", "WiFi", "Gym"),
            images = listOf(
                "https://images.unsplash.com/photo-1613490493576-7fde63acd811?q=80&w=2000",
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=2000"
            ),
            available = true,
            latitude = 16.8211,
            longitude = 96.1525,
            propertyType = PropertyType.CONDO.name,
            createdAt = "2023-05-20",
            updatedAt = "2023-05-20",
            appointmentStatus = null,
            isFavorite = false,
            isOwnProperty = false
        )
    )

    val appointments = listOf(
        Appointment(
            id = "1",
            renterName = "John Doe",
            renterProfileUrl = "https://example.com/profiles/john_doe.jpg",
            landlordName = "Jane Smith",
            landlordProfileUrl = "https://example.com/profiles/jane_smith.jpg",
            property = properties.get(0),
            status = AppointmentStatus.PENDING,
            fromDate = "2023-10-01",
            toDate = "2023-10-05",
            description = "Viewing appointment for a cozy studio apartment.",
            remark = "Renter prefers morning hours.",
            action = AppointmentAction.NONE
        ),
        Appointment(
            id = "2",
            renterName = "Alice Brown",
            renterProfileUrl = "https://example.com/profiles/alice_brown.jpg",
            landlordName = "Bob Johnson",
            landlordProfileUrl = "https://example.com/profiles/bob_johnson.jpg",
            property = properties.get(1),
            status = AppointmentStatus.ACCEPTED,
            fromDate = "2023-10-10",
            toDate = "2023-10-12",
            description = "Appointment to discuss rental terms for a modern condo.",
            remark = "Landlord requested a deposit discussion.",
            action = AppointmentAction.NONE
        ),
        Appointment(
            id = "3",
            renterName = "Charlie Davis",
            renterProfileUrl = "https://example.com/profiles/charlie_davis.jpg",
            landlordName = "Diana Evans",
            landlordProfileUrl = "https://example.com/profiles/diana_evans.jpg",
            property = properties.get(2),
            status = AppointmentStatus.REJECTED,
            fromDate = "2023-10-15",
            toDate = "2023-10-16",
            description = "Inquiry about luxury villa availability.",
            remark = "Landlord rejected due to unavailability.",
            action = AppointmentAction.NONE
        ),
        Appointment(
            id = "4",
            renterName = "Eve Foster",
            renterProfileUrl = "https://example.com/profiles/eve_foster.jpg",
            landlordName = "Frank Green",
            landlordProfileUrl = "https://example.com/profiles/frank_green.jpg",
            property = properties.get(3),
            status = AppointmentStatus.CANCELLED,
            fromDate = "2023-10-20",
            toDate = "2023-10-22",
            description = "Tour of an affordable room in a shared house.",
            remark = "Renter canceled due to scheduling conflict.",
            action = AppointmentAction.NONE
        )
    )
}