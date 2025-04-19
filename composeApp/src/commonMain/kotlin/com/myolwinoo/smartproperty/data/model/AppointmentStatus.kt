package com.myolwinoo.smartproperty.data.model

enum class AppointmentStatus(val rawValue: String) {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    CANCELLED("canceled");

    companion object {
        fun fromRawValue(rawValue: String?): AppointmentStatus? {
            return AppointmentStatus.entries.find { it.rawValue == rawValue }
        }
    }
}