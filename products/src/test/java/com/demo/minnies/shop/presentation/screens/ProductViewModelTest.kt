package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.domain.usescases.AddToCartUseCase
import com.demo.minnies.shop.domain.usescases.FetchProductByIdUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

internal class ProductViewModelTest {

    private val scheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = UnconfinedTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Test
    fun `test itemId is correctly picked from the savedStateHandle`() = runTest {

        val addToCartUseCase = object : AddToCartUseCase {
            override suspend fun invoke(productId: Int, quantity: Int, userId: Long?) = 0L
        }
        val fetchProductByIdUseCase = object : FetchProductByIdUseCase {
            override fun invoke(id: Int): Flow<ViewProduct> = emptyFlow()
        }
        val getCachedUserUseCase = object : GetCachedUserUseCase {
            override fun invoke(): Flow<PartialUser?> = emptyFlow()
        }
        val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
            override fun invoke(): Flow<String> = emptyFlow()
        }

        val productViewModel =
            ProductViewModel(
                savedStateHandle = SavedStateHandle(mapOf("id" to 1)),
                addToCartUseCase = addToCartUseCase,
                fetchProductByIdUseCase = fetchProductByIdUseCase,
                getCachedUserUseCase = getCachedUserUseCase,
                getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
            )

        Assert.assertEquals(1, productViewModel.itemId)
    }

    @Test
    fun `test that uiState moves from loading to success when product id from savedStateHandle returns product`() =
        runTest {
            val addToCartUseCase = object : AddToCartUseCase {
                override suspend fun invoke(productId: Int, quantity: Int, userId: Long?) = 0L
            }
            val fetchProductByIdUseCase = object : FetchProductByIdUseCase {
                override fun invoke(id: Int): Flow<ViewProduct> = flow {
                    emit(ViewProduct(id = id))
                }
            }
            val getCachedUserUseCase = object : GetCachedUserUseCase {
                override fun invoke(): Flow<PartialUser?> = flow { emit(null) }
            }
            val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
                override fun invoke(): Flow<String> = flow {
                    emit("USD")
                }
            }

            val productViewModel =
                ProductViewModel(
                    savedStateHandle = SavedStateHandle(mapOf("id" to 1)),
                    addToCartUseCase = addToCartUseCase,
                    fetchProductByIdUseCase = fetchProductByIdUseCase,
                    getCachedUserUseCase = getCachedUserUseCase,
                    getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
                )

            productViewModel.uiState.test {
                Assert.assertTrue(awaitItem() is ProductViewModel.UiState.Loading)
                Assert.assertTrue(awaitItem() is ProductViewModel.UiState.Success)
                awaitComplete()
            }
        }

    @Test
    fun `test that uiState moves from loading to error when fetch returns an error`() =
        runTest {
            val addToCartUseCase = object : AddToCartUseCase {
                override suspend fun invoke(productId: Int, quantity: Int, userId: Long?) = 0L
            }
            val fetchProductByIdUseCase = object : FetchProductByIdUseCase {
                override fun invoke(id: Int): Flow<ViewProduct> = flow {
                    throw CustomExceptions.NotFoundException("")
                }
            }
            val getCachedUserUseCase = object : GetCachedUserUseCase {
                override fun invoke(): Flow<PartialUser?> = flow { emit(null) }
            }
            val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
                override fun invoke(): Flow<String> = flow {
                    emit("USD")
                }
            }

            val productViewModel =
                ProductViewModel(
                    savedStateHandle = SavedStateHandle(mapOf("id" to 1)),
                    addToCartUseCase = addToCartUseCase,
                    fetchProductByIdUseCase = fetchProductByIdUseCase,
                    getCachedUserUseCase = getCachedUserUseCase,
                    getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
                )

            productViewModel.uiState.test {
                Assert.assertTrue(awaitItem() is ProductViewModel.UiState.Loading)
                Assert.assertTrue(awaitItem() is ProductViewModel.UiState.Error)
                awaitComplete()
            }
        }

    @Test
    fun `test that addProductToCart sends snack bar message when error occurs`() =
        runTest {

            Dispatchers.setMain(dispatcher)

            try {

                val addToCartUseCase = object : AddToCartUseCase {
                    override suspend fun invoke(productId: Int, quantity: Int, userId: Long?) = 0L
                }
                val fetchProductByIdUseCase = object : FetchProductByIdUseCase {
                    override fun invoke(id: Int): Flow<ViewProduct> = emptyFlow()
                }
                val getCachedUserUseCase = object : GetCachedUserUseCase {
                    override fun invoke(): Flow<PartialUser?> = emptyFlow()
                }
                val getUserCurrencyPreferenceUseCase = object : GetUserCurrencyPreferenceUseCase {
                    override fun invoke(): Flow<String> = emptyFlow()
                }

                val productViewModel =
                    ProductViewModel(
                        savedStateHandle = SavedStateHandle(mapOf("id" to 1)),
                        addToCartUseCase = addToCartUseCase,
                        fetchProductByIdUseCase = fetchProductByIdUseCase,
                        getCachedUserUseCase = getCachedUserUseCase,
                        getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
                    )

                val values = mutableListOf<String>()
                val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                    productViewModel.snackBarMessage.toList(values)
                }

                productViewModel.addToCart(1, 1, null)

                Assert.assertEquals(values.size, 1)

                collectJob.cancel()

            } finally {
                Dispatchers.resetMain()
            }
        }
}