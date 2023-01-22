package com.demo.minnies.presentation

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.domain.GetCachedUserUseCaseImpl
import com.demo.minnies.database.models.PartialUser
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
class MainActivityViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cacheRepo: CacheRepo

    @Inject
    lateinit var getCachedUserUseCaseImpl: GetCachedUserUseCaseImpl

    lateinit var viewModel: MainActivityViewModel

    private val dispatcher = UnconfinedTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = MainActivityViewModel(getCachedUserUseCaseImpl)
    }

    @Test
    fun loggedInUser_returnsNull_WhenNoUser() {
        scope.launch {
            cacheRepo.clear()
            Assert.assertNull(viewModel.loggedInUser.first())
        }
    }

    @Test
    fun loggedInUser_returnsUser_WhenCacheHasUser() {
        scope.launch {
            cacheRepo.storeLoggedInUser(PartialUser(fullName = "", emailAddress = "", phoneNumber = ""))
            Assert.assertNotNull(viewModel.loggedInUser.first())
        }
    }
}