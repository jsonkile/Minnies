package com.demo.minnies.shared.utils.validation

import org.junit.Assert
import org.junit.Test


internal class PasswordKtTest {

    @Test
    fun `test that validate password function correctly returns error when present`() {
        Assert.assertTrue("ghtt".validateAsPassword() != null)
    }

    @Test
    fun `test that validate password function correctly returns null when no error`() {
        Assert.assertTrue("jB3nkile@tmai".validateAsPassword() == null)
        Assert.assertTrue("8jU!ds".validateAsPassword() == null)
    }

    @Test
    fun `test that validate confirm password function correctly returns null when match`() {
        Assert.assertTrue("happy".validateAsConfirmPassword("happy") == null)
    }

    @Test
    fun `test that validate confirm password function correctly returns null when no match`() {
        Assert.assertTrue("hay".validateAsConfirmPassword("happy") != null)
    }
}