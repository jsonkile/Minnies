package com.demo.minnies.shop.presentation.models

import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.data.models.ShopItem
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewShopItemKtTest {

    @Test
    fun `verify that forView function correctly converts ShopItem to ViewShopItem`() {
        val shopItem =
            ShopItem(
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

        val viewShopItem = shopItem.toView()
        assertEquals(0, shopItem.id)
        assertEquals("X", shopItem.name)
        assertEquals("Z", shopItem.description)
        assertEquals(listOf(0, 1), shopItem.sizes)
        assertEquals("$0.20", viewShopItem.price)
        assertEquals(false, shopItem.featured)
        assertEquals(9.0, shopItem.rating, 0.0)
        assertEquals("Y", shopItem.image)
    }

}