package com.myolwinoo.smartproperty.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterRoundHalfUp

actual object DecimalFormatter {

    actual fun format(value: Double): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterDecimalStyle
        formatter.groupingSeparator = ","
        formatter.usesGroupingSeparator = true
        return formatter.stringFromNumber(NSNumber(value))!!
    }

    actual fun formatToOneDecimal(value: Double): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterDecimalStyle
        formatter.minimumFractionDigits = 1uL
        formatter.maximumFractionDigits = 1uL

        val number = NSNumber(value)
        return formatter.stringFromNumber(number) ?: value.toString()
    }
}