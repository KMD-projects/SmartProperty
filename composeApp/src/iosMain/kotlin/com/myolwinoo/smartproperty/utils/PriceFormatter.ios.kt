package com.myolwinoo.smartproperty.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual object PriceFormatter {

    actual fun format(value: Double): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterDecimalStyle
        formatter.groupingSeparator = ","
        formatter.usesGroupingSeparator = true
        return formatter.stringFromNumber(NSNumber(value))!!
    }
}