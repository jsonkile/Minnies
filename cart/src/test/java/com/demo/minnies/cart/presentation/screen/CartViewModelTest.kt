package com.demo.minnies.cart.presentation.screen

import app.cash.turbine.test
import com.demo.minnies.cart.domain.usecases.DeleteCartItemUseCase
import com.demo.minnies.cart.domain.usecases.FetchCartUseCase
import com.demo.minnies.cart.presentation.screens.cart.CartViewModel
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.database.models.CartItemIdAndQuantity
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.CustomExceptions
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

        val deleteCartItemUseCase = object : DeleteCartItemUseCase {
            override suspend fun invoke(id: Long) {}
        }

        val updateCartItemUseCase = object :
            com.demo.minnies.cart.domain.usecases.UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: CartItemIdAndQuantity) {}
        }

        val cartViewModel = CartViewModel(
            fetchCartUseCase,
            updateCartItemUseCase,
            deleteCartItemUseCase
        )

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Success)
            awaitComplete()
        }
    }

    @Test
    fun `test that uiState goes from loading to empty when cart it empty`() = runTest {
        val fetchCartUseCase = object : com.demo.minnies.cart.domain.usecases.FetchCartUseCase {
            override fun invoke(): Flow<List<ViewCartItem>> = flow { emit(emptyList()) }
        }

        val updateCartItemUseCase = object :
            com.demo.minnies.cart.domain.usecases.UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: CartItemIdAndQuantity) {}
        }

        val deleteCartItemUseCase = object : DeleteCartItemUseCase {
            override suspend fun invoke(id: Long) {}
        }

        val cartViewModel = CartViewModel(
            fetchCartUseCase,
            updateCartItemUseCase,
            deleteCartItemUseCase
        )

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Empty)
            awaitComplete()
        }
    }

    @Test
    fun `test that uiState goes from loading to error when there is an exception`() = runTest {
        val fetchCartUseCase = object : com.demo.minnies.cart.domain.usecases.FetchCartUseCase {
            override fun invoke(): Flow<List<ViewCartItem>> = flow {
                throw CustomExceptions.BadRequestException(message = "problem")
            }
        }

        val updateCartItemUseCase = object :
            com.demo.minnies.cart.domain.usecases.UpdateCartItemUseCase {
            override suspend fun invoke(cartItem: CartItemIdAndQuantity) {}
        }

        val deleteCartItemUseCase = object : DeleteCartItemUseCase {
            override suspend fun invoke(id: Long) {}
        }

        val cartViewModel = CartViewModel(
            fetchCartUseCase,
            updateCartItemUseCase,
            deleteCartItemUseCase
        )

        cartViewModel.uiState.test {
            Assert.assertTrue(awaitItem() is CartViewModel.UiState.Loading)
            Assert.assertEquals(
                "problem",
                (awaitItem() as CartViewModel.UiState.Error).throwable.message
            )
            awaitComplete()
        }
    }
}