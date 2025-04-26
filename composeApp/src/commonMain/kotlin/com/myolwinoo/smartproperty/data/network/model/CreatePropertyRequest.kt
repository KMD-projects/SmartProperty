package com.myolwinoo.smartproperty.data.network.model

import com.myolwinoo.smartproperty.data.model.PropertyImage

data class CreatePropertyRequest(
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val address: String? = null,
    val propertyTypeId: String? = null,
    val amenityIds: List<String>? = null,
    val images: List<PropertyImage.Local>? = null
)
