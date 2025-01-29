package com.myolwinoo.smartproperty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform