package com.demo.minnies.shop.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.database.room.models.PartialCartItem
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.domain.usescases.FetchCartUseCase
import com.demo.minnies.shop.domain.usescases.UpdateCartItemUseCase
import com.demo.minnies.shop.presentation.models.ViewCartItem
import com.demo.minnies.shop.presentation.screens.cart.CartViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class CartViewModelTest {

    @Test
    fun `test that uiState goes from loading to success when cart it not empty`() = runTest {
        val fetchCartUseCase = object : FetchCartUseCase {
            override fun invoke(): Flow<List<ViewCartItem>> = flow {
                emit(
                    listOf(
                        ViewCartItem(
                            id = 0L,
                            quantity = 1,
                            productId = 1,
                            productName = "",
                            productImage = "",
                            baseProductPrice = 5.0,
                            formattedTotalAmount = "",
                            formattedProductPrice = "",
                            convertedProductPrice = 5.0,
                            currency = Currency.USD
                        )
                    )
                )
            }
        }

        val updateCartItemUseCase = object : UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: PartialCartItem) {}
        }

        val cartViewModel = CartViewModel(fetchCartUseCase, updateCartItemUseCase)

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Success)
            awaitComplete()
        }
    }


    @Test
    fun `test that uiState goes from loading to empty when cart it empty`() = runTest {
        val fetchCartUseCase = object : FetchCartUseCase {
            override fun invoke(): Flow<List<ViewCartItem>> = flow { emit(emptyList()) }
        }

        val updateCartItemUseCase = object : UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: PartialCartItem) {}
        }

        val cartViewModel = CartViewModel(fetchCartUseCase, updateCartItemUseCase)

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Empty)
            awaitComplete()
        }
    }


    @Test
    fun `test that uiState goes from loading to error when there is an exception`() = runTest {
        val fetchCartUseCase = object : FetchCartUseCase {
            override fun invoke(): Flow<List<ViewCartItem>> = flow {
                throw CustomExceptions.BadRequestException(message = "problem")
            }
        }

        val updateCartItemUseCase = object : UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: PartialCartItem) {}
        }

        val cartViewModel = CartViewModel(fetchCartUseCase, updateCartItemUseCase)

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertEquals(
                "problem", (awaitItem() as CartViewModel.UiState.Error).throwable.message,
            )
            awaitComplete()
        }
    }
}