package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.models.PartialUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class CacheRepoDataStoreImplTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cacheRepoDataStoreImpl: CacheRepoDataStoreImpl

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun cleanUp() {
        scope.cancel()
    }

    @Test
    fun storeLoggedInUserAndGetLoggedInUser_SaveAndReturnsAccurateData() {
        scope.launch {
            cacheRepoDataStoreImpl.storeLoggedInUser(
                PartialUser(
                    fullName = "joha",
                    emailAddress = "joha@gmail.com",
                    phoneNumber = "77"
                )
            )

            assertEquals("joha", cacheRepoDataStoreImpl.getLoggedInUser().first()?.fullName)
            assertEquals(
                "joha@gmail.com",
                cacheRepoDataStoreImpl.getLoggedInUser().first()?.emailAddress
            )
            assertEquals("77", cacheRepoDataStoreImpl.getLoggedInUser().first()?.phoneNumber)
        }
    }

    @Test
    fun getLoggedInUser_ReturnsNullByDefault() {
        scope.launch {
            assertNull(cacheRepoDataStoreImpl.getLoggedInUser().first())
        }
    }

    @Test
    fun clear_CorrectlyRemovesDataFromCache() {
        scope.launch {
            cacheRepoDataStoreImpl.storeLoggedInUser(
                PartialUser(
                    fullName = "A",
                    emailAddress = "B",
                    phoneNumber = "C",
                )
            )

            assertEquals("A", cacheRepoDataStoreImpl.getLoggedInUser().first()?.fullName)
            cacheRepoDataStoreImpl.clear()
            assertNull(cacheRepoDataStoreImpl.getLoggedInUser())
        }
    }
}