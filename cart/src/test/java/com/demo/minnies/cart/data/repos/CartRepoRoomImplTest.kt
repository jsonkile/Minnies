package com.demo.minnies.cart.data.repos

import com.demo.minnies.database.models.CartItem
import com.demo.minnies.database.models.CartItemIdAndQuantity
import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.OrdersDao
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.*

internal class CartRepoRoomImplTest {

    @get:Rule
    val mockRule = MockKRule(this)

    @MockK
    lateinit var cartDao: CartDao

    @MockK
    lateinit var ordersDao: OrdersDao

    private lateinit var cartRepoRoomImpl: CartRepoRoomImpl

    private val items = mutableListOf<CartItem>()

    @Before
    fun setup() {
        cartRepoRoomImpl = CartRepoRoomImpl(cartDao, ordersDao)
    }

    @After
    fun cleanUp() {
        items.clear()
    }

    @Test
    fun testAddItem() = runTest {
        val addItem = CartItem(productId = 1, quantity = 1, userId = 1)
        coEvery { cartDao.insert(any()) } returns 1L
        Assert.assertTrue(cartRepoRoomImpl.addItem(addItem) == 1L)
    }

    @Test
    fun testUpdateQuantity() = runTest {
        var step = 0
        coEvery { cartDao.updateQuantity(CartItemIdAndQuantity(1, 2)) } answers { step++ }
        cartRepoRoomImpl.updateQuantity(CartItemIdAndQuantity(1, 2))
        Assert.assertEquals(1, step)
    }

    @Test
    fun testRemoveItem() = runTest {
        var step = 0
        coEvery { cartDao.delete(any()) } answers { step++ }
        cartRepoRoomImpl.removeItem(9)
        Assert.assertEquals(1, step)
    }

    @Test
    fun testGetItem() = runTest {
        val item = CartItem(userId = 1, productId = 1, quantity = 1)
        coEvery { cartDao.get(any()) } returns item
        Assert.assertEquals(
            CartItem(userId = 1, productId = 1, quantity = 1),
            cartRepoRoomImpl.getItem(9)
        )
    }
}