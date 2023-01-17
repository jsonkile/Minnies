package com.demo.minnies.shop.domain.usescases

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class FetchFeaturedShopItemsUseCaseImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var fetchFeaturedShopItemsUseCaseImpl: FetchFeaturedShopItemsUseCaseImpl

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun useCase_correctlyFetchesFeaturedShopItems() = runTest {
        val items = fetchFeaturedShopItemsUseCaseImpl().first()
        Assert.assertEquals(true, items.isNotEmpty())
        Assert.assertTrue(items.none { !it.featured })
    }
}