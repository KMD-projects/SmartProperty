package com.myolwinoo.smartproperty.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilsTest {

    @Test
    fun testToDisplayDate() {
        assertEquals(
            "Mar 19, 2025 at 5:39 AM",
            DateUtils.toDisplayDate("2025-03-18T22:39:00.000000Z")
        )
    }

    @Test
    fun testToServerDate() {
        assertEquals(
            "2025-03-18 22:39:00",
            DateUtils.toServerDate("2025-03-19 05:39:00")
        )
    }
}