package com.demo.minnies.shared.utils.validation

import org.junit.Assert
import org.junit.Test


internal class PhoneKtTest {

    @Test
    fun `test that validate phone number function correctly returns error when present`() {
        Assert.assertTrue("".validateAsPhoneNumber() != null)
    }

    @Test
    fun `test that validate phone number function correctly returns null when no error`() {
        Assert.assertTrue("888804894432".validateAsPhoneNumber() == null)
    }

}