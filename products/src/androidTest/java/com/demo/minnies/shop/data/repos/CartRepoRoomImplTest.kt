package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.models.CartItem
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.PartialCartItem
import com.demo.minnies.database.room.models.Product
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
internal class CartRepoRoomImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var cartRepoRoomImpl: CartRepoRoomImpl

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }


    @Test
    fun testCartRepo() = runTest {

        Assert.assertEquals(0, cartRepoRoomImpl.getAllItems().first().size)

        cartRepoRoomImpl.addItem(CartItem(4, 2, 2, 2))

        Assert.assertEquals(1, cartRepoRoomImpl.getAllItems().first().size)

        cartRepoRoomImpl.removeItem(4)

        Assert.assertEquals(0, cartRepoRoomImpl.getAllItems().first().size)

        cartRepoRoomImpl.addItem(CartItem(4, 2, 4, 2))
        cartRepoRoomImpl.updateItem(PartialCartItem(4, 1))

        Assert.assertEquals(1, cartRepoRoomImpl.getAllItems().first().first().quantity)
    }
}