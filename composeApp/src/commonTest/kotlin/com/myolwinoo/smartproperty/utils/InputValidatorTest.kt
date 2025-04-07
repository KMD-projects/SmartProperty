package com.myolwinoo.smartproperty.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InputValidatorTest {

    @Test
    fun testValidEmail() {
        assertTrue(InputValidator.isValidEmail("example@example.com"))
        assertTrue(InputValidator.isValidEmail("user.name+tag+sorting@example.com"))
        assertTrue(InputValidator.isValidEmail("user_name@example.co.in"))
    }

    @Test
    fun testInvalidEmail() {
        assertFalse(InputValidator.isValidEmail("plainaddress"))
        assertFalse(InputValidator.isValidEmail("@missingusername.com"))
        assertFalse(InputValidator.isValidEmail("username@.com"))
        assertFalse(InputValidator.isValidEmail("username@.com."))
    }

    @Test
    fun testValidPassword() {
        assertTrue(InputValidator.isValidPassword("Valid@123"))
        assertTrue(InputValidator.isValidPassword("Another1!"))
        assertTrue(InputValidator.isValidPassword("Passw0rd!"))
    }

    @Test
    fun testInvalidPassword() {
        assertFalse(InputValidator.isValidPassword("short1!"))
        assertFalse(InputValidator.isValidPassword("NoSpecialChar1"))
        assertFalse(InputValidator.isValidPassword("nouppercase1!"))
        assertFalse(InputValidator.isValidPassword("NOLOWERCASE1!"))
        assertFalse(InputValidator.isValidPassword("NoDigit!"))
    }
}