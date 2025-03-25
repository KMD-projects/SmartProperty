package com.myolwinoo.smartproperty.utils

import java.text.DecimalFormat

actual object PriceFormatter {

    actual fun format(value: Double): String {
        val df = DecimalFormat("#,###")
        return df.format(value)
    }
}