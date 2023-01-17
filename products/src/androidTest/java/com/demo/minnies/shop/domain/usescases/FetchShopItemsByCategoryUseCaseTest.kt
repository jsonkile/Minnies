package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.room.models.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class FetchShopItemsByCategoryUseCaseTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var fetchShopItemsByCategoryUseCase: FetchShopItemsByCategoryUseCase

    @After
    fun cleanUp() {
        scope.cancel()
    }

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun useCase_correctlyFetchesProductsByCategory() {
        scope.launch {
            val items = fetchShopItemsByCategoryUseCase(Category.Top).first()
            Assert.assertTrue(items.isNotEmpty())
            Assert.assertTrue(items.none { it.category != Category.Top })
        }
    }
}