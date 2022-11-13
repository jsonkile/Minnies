package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class RegisterUserUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var registerUserUseCase: RegisterUserUseCase

    @Inject
    lateinit var cacheRepo: CacheRepo

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun register_SuccessfullyRegisterUser_AndCacheReturnsUser() {
        scope.launch {
            registerUserUseCase(
                email = "X",
                password = "B",
                fullName = "Z",
                phoneNumber = "Q"
            )

            Assert.assertEquals("X", cacheRepo.getLoggedInUser().first()?.emailAddress)
            Assert.assertEquals("Z", cacheRepo.getLoggedInUser().first()?.fullName)
            Assert.assertEquals("Q", cacheRepo.getLoggedInUser().first()?.phoneNumber)
        }
    }

}