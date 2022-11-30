package com.demo.minnies.shared.utils.validation

import org.junit.Assert
import org.junit.Test


internal class EmailKtTest {

    @Test
    fun `test that validate email function correctly returns error when present`() {
        Assert.assertTrue("".validateAsEmail() != null)
    }

    @Test
    fun `test that validate email function correctly returns null when no error`() {
        Assert.assertTrue("jsonkile@tmail.co".validateAsEmail() == null)
    }

}