package com.myolwinoo.smartproperty.data.model

data class Appointment(
    val id: String,
    val renterName: String,
    val renterProfileUrl: String,
    val landlordName: String,
    val landlordProfileUrl: String,
    val property: Property,
    val status: AppointmentStatus?,
    val fromDate: String,
    val toDate: String,
    val description: String,
    val remark: String,
    val action: AppointmentAction
)

enum class AppointmentAction {
    ACCEPT_REJECT,
    CANCEL,
    NONE
}