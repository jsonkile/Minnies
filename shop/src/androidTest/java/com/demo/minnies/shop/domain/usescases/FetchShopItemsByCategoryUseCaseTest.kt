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
internal class FetchShopItemsByCategoryUseCaseTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var fetchShopItemsByCategoryUseCase: FetchShopItemsByCategoryUseCase

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun useCase_correctlyFetchesProductsByCategory() = runTest {
        val items = fetchShopItemsByCategoryUseCase(Category.Top).first()
        Assert.assertTrue(items.isNotEmpty())
        Assert.assertTrue(items.none { it.category != Category.Top })
    }
}