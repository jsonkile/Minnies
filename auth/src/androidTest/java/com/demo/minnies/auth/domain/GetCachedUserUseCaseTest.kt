package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.database.room.models.PartialUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class GetCachedUserUseCaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cacheRepo: CacheRepo

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun get_ReturnsNull_WhenNoUserInCache() {
        scope.launch {
            cacheRepo.clear()
            Assert.assertNull(cacheRepo.getLoggedInUser().first())
        }
    }

    @Test
    fun get_ReturnsUser_WhenUserInCache() {
        scope.launch {
            cacheRepo.storeLoggedInUser(
                PartialUser(
                    id = 0,
                    fullName = "",
                    emailAddress = "",
                    phoneNumber = ""
                )
            )
            Assert.assertNotNull(cacheRepo.getLoggedInUser().first())
        }
    }
}