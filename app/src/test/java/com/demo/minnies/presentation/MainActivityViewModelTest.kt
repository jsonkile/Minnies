package com.demo.minnies.presentation

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.auth.domain.GetUserUseCase
import com.demo.minnies.auth.domain.LogoutUserUseCase
import com.demo.minnies.auth.domain.UpdateCachedUserUseCase
import com.demo.minnies.database.models.PartialUser
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class MainActivityViewModelTest {
    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var getCachedUserUseCase: GetCachedUserUseCase

    @MockK
    lateinit var getUserUseCase: GetUserUseCase

    @MockK
    lateinit var updateCachedUserUseCase: UpdateCachedUserUseCase

    @MockK
    lateinit var logoutUserUseCase: LogoutUserUseCase

    @Test
    fun testVertCacheUser_CachedUserNotFound_Logout() = run {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(TestCoroutineScheduler()))

            var running = true

            val mockUser = PartialUser(emailAddress = "email", phoneNumber = "phone", fullName = "")

            coEvery { getCachedUserUseCase() } returns flow { emit(mockUser) }
            coEvery { getUserUseCase(any()) } returns flow { emit(null) }
            coEvery { logoutUserUseCase() } answers { running = false }
            coEvery { updateCachedUserUseCase(mockUser) }

            MainActivityViewModel(
                getUserUseCase,
                getCachedUserUseCase,
                logoutUserUseCase,
                updateCachedUserUseCase
            )

            Assert.assertFalse(running)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testVertCacheUser_CachedUserFound_UpdateCache() = run {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(TestCoroutineScheduler()))

            var running = true

            val mockUser = PartialUser(emailAddress = "email", phoneNumber = "phone", fullName = "")

            coEvery { getCachedUserUseCase() } returns flow { emit(mockUser) }
            coEvery { getUserUseCase(any()) } returns flow { emit(mockUser) }

            coEvery { updateCachedUserUseCase(mockUser) } answers { running = false }

            MainActivityViewModel(
                getUserUseCase,
                getCachedUserUseCase,
                logoutUserUseCase,
                updateCachedUserUseCase
            )

            Assert.assertFalse(running)

        } finally {
            Dispatchers.resetMain()
        }
    }
}