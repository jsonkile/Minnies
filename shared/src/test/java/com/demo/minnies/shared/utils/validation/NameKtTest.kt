package com.demo.minnies.shared.utils.validation

import org.junit.Assert
import org.junit.Test


internal class NameKtTest {

    @Test
    fun `test that validate name function correctly returns error when present`() {
        Assert.assertTrue("@jah".validateAsName() != null)
        Assert.assertTrue("ah".validateAsName() != null)
        Assert.assertTrue("3anaah".validateAsName() != null)
        Assert.assertTrue(" hanaah".validateAsName() != null)
        Assert.assertTrue("hanaah".validateAsName() != null)
        Assert.assertTrue(" ".validateAsName() != null)
        Assert.assertTrue("dg o".validateAsName() != null)
    }

    @Test
    fun `test that validate name function correctly returns null when no error`() {
        Assert.assertTrue("json kile".validateAsName() == null)
    }

}