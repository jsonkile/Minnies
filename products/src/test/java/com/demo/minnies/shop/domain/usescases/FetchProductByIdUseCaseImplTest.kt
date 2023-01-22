package com.demo.minnies.shop.domain.usescases

import com.demo.minnies.database.models.CartItem
import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shop.data.repos.ProductsRepo
import com.demo.minnies.shop.util.mockProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class FetchProductByIdUseCaseImplTest {

    @Test
    fun `test that use case returns correct product`() = runTest {
        val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
            override fun invoke() = flow { emit("USD") }
        }

        val productsRepo = object : ProductsRepo {
            val items = listOf(
                Product(
                    id = 1,
                    category = Category.Accessory,
                    description = "",
                    name = "mimic",
                    image = "",
                    price = 0.0
                )
            )

            override suspend fun addItem(product: Product): Long = 0L

            override suspend fun addItems(products: List<Product>): List<Long> = emptyList()

            override fun getAllItems(): Flow<List<Product>> = emptyFlow()

            override fun getItemsByCategory(category: Category): Flow<List<Product>> = emptyFlow()

            override fun getFeaturedItems(): Flow<List<Product>> = emptyFlow()

            override fun getProductById(id: Int): Flow<Product?> = flow { emit(items.firstOrNull { it.id == id }) }

            override fun searchProducts(term: String): Flow<List<Product>> = emptyFlow()

            override suspend fun addToCart(cartItem: CartItem): Long = 0L

            override suspend fun checkAddedToCart(productId: Int, userId: Long): Boolean = true
        }

        val fetchProductByIdUseCase = FetchProductByIdUseCaseImpl(
            productsRepo,
            getUserCurrencyPreferenceUseCase
        )

        Assert.assertTrue(fetchProductByIdUseCase(1).first().name == "mimic")
    }
}