package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepoRoomImpl
import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.database.room.models.PartialUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
class LogoutUserUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var logoutUserUseCase: LogoutUserUseCase

    @Inject
    lateinit var cacheRepo: CacheRepo

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun logout_RemovesUserFromDataStore() {
        scope.launch {
            cacheRepo.storeLoggedInUser(
                PartialUser(
                    fullName = "b",
                    phoneNumber = "b",
                    emailAddress = "b"
                )
            )
            logoutUserUseCase()
            Assert.assertEquals(null, cacheRepo.getLoggedInUser().first())
        }
    }
}
