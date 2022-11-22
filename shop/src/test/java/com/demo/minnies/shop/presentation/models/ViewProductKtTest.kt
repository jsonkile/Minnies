package com.demo.minnies.shop.presentation.models

import com.demo.minnies.database.room.models.Category
import com.demo.minnies.database.room.models.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewProductKtTest {

    @Test
    fun `verify that forView function correctly converts ShopItem to ViewShopItem`() {
        val product =
            Product(
                0,
                name = "X",
                image = "Y",
                description = "Z",
                sizes = listOf(0, 1),
                category = Category.Shorts,
                price = 0.2,
                featured = false,
                rating = 9.0
            )

        val viewShopItem = product.toView()
        assertEquals(0, product.id)
        assertEquals("X", product.name)
        assertEquals("Z", product.description)
        assertEquals(listOf(0, 1), product.sizes)
        assertEquals("$0.20", viewShopItem.price)
        assertEquals(false, product.featured)
        assertEquals(9.0, product.rating, 0.0)
        assertEquals("Y", product.image)
    }

}