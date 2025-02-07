package com.myolwinoo.smartproperty.data.network

import com.myolwinoo.smartproperty.PreviewData
import com.myolwinoo.smartproperty.data.model.Property
import kotlinx.coroutines.delay

class SPApi(

) {
    suspend fun getPropertyList(): Result<List<Property>> {
        delay(1000)
        return Result.success(PreviewData.properties)
    }

    suspend fun getWishlists(): Result<List<Property>> {
        return getPropertyList()
            .map { it.filter { it.isFavorite } }
    }
}