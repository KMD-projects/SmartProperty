package com.myolwinoo.smartproperty.data.model

sealed class PropertyImage {
    data class Local(val id: String, val data: ByteArray) : PropertyImage()
    data class Remote(val id: String, val url: String) : PropertyImage()

    val imageId: String
        get() = when (this) {
            is Local -> id
            is Remote -> id
        }

    val imageData
        get() = when (this) {
            is Local -> data
            is Remote -> url
        }
}