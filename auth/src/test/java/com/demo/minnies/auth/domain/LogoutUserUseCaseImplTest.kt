package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class LogoutUserUseCaseImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var cacheRepo: CacheRepo

    @Test
    fun testLogout() = runTest {
        var running = true
        coEvery { cacheRepo.clear() } answers { running = !running }
        val logoutUserUseCase = LogoutUserUseCaseImpl(cacheRepo)
        logoutUserUseCase()
        Assert.assertFalse(running)
    }
}