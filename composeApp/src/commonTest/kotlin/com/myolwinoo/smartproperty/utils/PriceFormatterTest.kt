package com.myolwinoo.smartproperty.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class PriceFormatterTest {

    @Test
    fun testFormatPrice() {
        val decimalValue = 10000.00
        val formattedValue = PriceFormatter.format(decimalValue)
        assertEquals("10,000", formattedValue)
    }
}