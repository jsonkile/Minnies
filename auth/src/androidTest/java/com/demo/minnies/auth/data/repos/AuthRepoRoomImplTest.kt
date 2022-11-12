package com.demo.minnies.auth.data.repos

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class AuthRepoRoomImplTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var authRepoRoomImpl: AuthRepoRoomImpl

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun login_CorrectlyLogsUserInAndDataStoreReturnsUser() = runTest {
        authRepoRoomImpl.register(
            emailAddress = "A",
            password = "B",
            fullName = "C",
            phoneNumber = "B"
        )

        assertEquals(
            "A",
            authRepoRoomImpl.login(emailAddress = "A", password = "B").first()?.emailAddress
        )

        assertEquals(
            "C",
            authRepoRoomImpl.login(emailAddress = "A", password = "B").first()?.fullName
        )

        assertEquals(
            "B",
            authRepoRoomImpl.login(emailAddress = "A", password = "B").first()?.phoneNumber
        )
    }

}