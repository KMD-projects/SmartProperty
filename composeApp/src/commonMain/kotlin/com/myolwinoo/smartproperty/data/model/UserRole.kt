package com.myolwinoo.smartproperty.data.model

enum class UserRole(val rawValue: String) {
    RENTER("renter"),
    LANDLORD("landlord");

    companion object {
        fun fromRawValue(rawValue: String?): UserRole {
            return UserRole.entries.find { it.rawValue == rawValue }!!
        }
    }
}