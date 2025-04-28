package com.myolwinoo.smartproperty.utils

import java.text.DecimalFormat


actual object DecimalFormatter {

    actual fun format(value: Double): String {
        val df = DecimalFormat("#,###")
        return df.format(value)
    }

    actual fun formatToOneDecimal(value: Double): String {
        val df = DecimalFormat("#.#")
        return df.format(value)
    }
}