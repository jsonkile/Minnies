package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.ProductCategory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import org.junit.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class FetchProductsByCategoryUseCaseTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var fetchProductsByCategoryUseCase: FetchProductsByCategoryUseCase

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
            val items = fetchProductsByCategoryUseCase(ProductCategory.Top).first()
            Assert.assertTrue(items.isNotEmpty())
            Assert.assertTrue(items.none { it.productCategory != ProductCategory.Top })
        }
    }
}