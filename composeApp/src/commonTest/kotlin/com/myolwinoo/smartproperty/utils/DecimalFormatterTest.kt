package com.myolwinoo.smartproperty.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class DecimalFormatterTest {

    @Test
    fun testFormatPrice() {
        val decimalValue = 10000.00
        val formattedValue = DecimalFormatter.format(decimalValue)
        assertEquals("10,000", formattedValue)
    }

    @Test
    fun testFormatRating() {
        val decimalValue = 33.333333
        val formattedValue = DecimalFormatter.formatToOneDecimal(decimalValue)
        assertEquals("33.3", formattedValue)
    }
}