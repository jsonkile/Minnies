package com.demo.minnies.shop.domain.usescases

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
internal class FetchProductByIdUseCaseImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Inject
    lateinit var fetchProductByIdUseCaseImpl: FetchProductByIdUseCaseImpl

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @After
    fun cleanUp() {
        scope.cancel()
    }

    @Test
    fun useCase_correctlyFetchesProductById() {
        scope.launch {
            val item = fetchProductByIdUseCaseImpl(4).first()
            Assert.assertEquals("Vans Retro", item.name)
        }
    }
}