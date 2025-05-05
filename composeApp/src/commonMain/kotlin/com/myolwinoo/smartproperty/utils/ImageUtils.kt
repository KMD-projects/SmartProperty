package com.myolwinoo.smartproperty.utils

import com.myolwinoo.smartproperty.AppConfiguration
import io.github.aakira.napier.Napier

object ImageUtils {

    private val base = AppConfiguration.BASE_URL
    private val baseDropped = base.dropLast(1)

    fun getFullUrl(url: String): String {
        return url.let { "$baseDropped$it" }
            .replace("http://smart-property.test/", base)
            .also { Napier.i("Full Image URL: $it") }
    }
}