package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.room.models.Category
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
internal class FetchShopItemsByCategoriesUseCaseTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var fetchShopItemsByCategoriesUseCase: FetchShopItemsByCategoriesUseCase

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun useCase_correctlyFetchesItemsByCategories() = runTest {
        val items = fetchShopItemsByCategoriesUseCase().first()
        Assert.assertEquals(true, items[Category.Top]?.isNotEmpty())
        Assert.assertEquals(true, items[Category.Kicks]?.isNotEmpty())
        Assert.assertEquals(true, items[Category.Accessory]?.isNotEmpty())
        Assert.assertEquals(true, items[Category.Shorts]?.isNotEmpty())
    }
}