package com.demo.minnies.shop.data.repos

import com.demo.minnies.database.room.daos.CartDao
import com.demo.minnies.database.room.daos.ProductsDao
import com.demo.minnies.database.models.CartItem
import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product
import javax.inject.Inject

class ProductsRepoRoomImpl @Inject constructor(
    private val productsDao: ProductsDao, private val cartDao: CartDao
) : ProductsRepo {

    override suspend fun addItem(product: Product) = productsDao.insert(product)

    override suspend fun addItems(products: List<Product>) = productsDao.insert(products)

    override fun getAllItems() = productsDao.getAll()

    override fun getItemsByCategory(category: Category) = productsDao.getItemsByCategory(category)

    override fun getFeaturedItems() = productsDao.getFeaturedItems()

    override fun getProductById(id: Int) = productsDao.get(id)

    override fun searchProducts(term: String) = productsDao.searchProducts("%$term%")

    override suspend fun addToCart(cartItem: CartItem) = cartDao.insert(cartItem)

    override suspend fun checkAddedToCart(productId: Int, userId: Long) =
        cartDao.getByProductAndUser(productId, userId) != null
}