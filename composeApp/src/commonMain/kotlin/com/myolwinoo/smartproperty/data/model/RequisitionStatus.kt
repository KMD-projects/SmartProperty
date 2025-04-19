package com.myolwinoo.smartproperty.data.model

enum class RequisitionStatus(val rawValue: String) {
    PENDING("pending"),
    REJECTED("rejected");

    companion object {
        fun fromRawValue(rawValue: String?): RequisitionStatus? {
            return RequisitionStatus.entries.find { it.rawValue == rawValue }
        }
    }
}