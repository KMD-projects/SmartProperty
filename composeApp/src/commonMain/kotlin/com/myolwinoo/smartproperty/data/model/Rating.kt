package com.myolwinoo.smartproperty.data.model

data class Rating(
    val id:String,
    val rating: Float,
    val comment: String,
    val username: String,
    val profilePic: String,
    val isUserComment: Boolean
)
