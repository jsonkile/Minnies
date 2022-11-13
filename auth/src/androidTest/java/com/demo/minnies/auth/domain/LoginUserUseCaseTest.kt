package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepoRoomImpl
import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.utils.CustomExceptions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.function.ThrowingRunnable
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class LoginUserUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var loginUserUseCase: LoginUserUseCase

    @Inject
    lateinit var authRepoRoomImpl: AuthRepoRoomImpl

    @Inject
    lateinit var cacheRepo: CacheRepo

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun login_SuccessfullyLogInUser_AndCacheReturnsCorrectUser_WhenDetailsMatch() {
        scope.launch {
            authRepoRoomImpl.register(
                emailAddress = "A",
                password = "N",
                fullName = "C",
                phoneNumber = "T"
            )

            loginUserUseCase(email = "A", password = "N")

            Assert.assertEquals("A", cacheRepo.getLoggedInUser().first()?.emailAddress)
            Assert.assertEquals("C", cacheRepo.getLoggedInUser().first()?.fullName)
            Assert.assertEquals("T", cacheRepo.getLoggedInUser().first()?.phoneNumber)
        }
    }

    @Test
    fun login_Fails_WhenDetailsDoNotMatch() {
        scope.launch {
            Assert.assertThrows(
                CustomExceptions.NotFoundException::class.java,
                ThrowingRunnable { runBlocking { loginUserUseCase(email = "V", password = "B") } })
        }
    }

}