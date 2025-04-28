package com.myolwinoo.smartproperty.utils

expect object DecimalFormatter {

    fun format(value: Double): String

    fun formatToOneDecimal(value: Double): String
}