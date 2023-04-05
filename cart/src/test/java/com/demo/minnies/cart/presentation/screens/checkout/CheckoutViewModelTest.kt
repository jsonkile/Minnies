package com.demo.minnies.cart.presentation.screens.checkout

import app.cash.turbine.test
import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.MainDispatcherRule
import com.demo.minnies.cart.domain.usecases.CheckoutCartUseCase
import com.demo.minnies.cart.domain.usecases.FetchCartUseCase
import com.demo.minnies.cart.domain.usecases.FetchCartUseCaseMockImpl
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.shared.domain.FetchDeliveryFeeUseCase
import com.demo.minnies.shared.domain.GetUserCurrencyPreferenceUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class CheckoutViewModelTest {

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = UnconfinedTestDispatcher(scheduler)

    @get:Rule
    val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockRule = MockKRule(this)

    @MockK
    lateinit var fetchCartUseCase: FetchCartUseCase

    @MockK
    lateinit var checkoutCartUseCase: CheckoutCartUseCase

    @MockK
    lateinit var fetchDeliveryFeeUseCase: FetchDeliveryFeeUseCase

    @MockK(relaxed = true)
    lateinit var getCachedUserUseCase: GetCachedUserUseCase

    @MockK
    lateinit var getUserCurrencyPreferenceUseCase: GetUserCurrencyPreferenceUseCase

    @Test
    fun testLoadCart() = runTest {
        val mockCart = listOf(
            ViewCartItem(
                quantity = 1, productId = 1, id = 1,
                productImage = "image",
                productName = "name",
                baseProductPrice = 0.1,
                formattedTotalAmount = "$0.1",
                formattedProductPrice = "$0.1"
            )
        )

        coEvery { fetchCartUseCase() } returns flow { emit(mockCart) }

        coEvery { getCachedUserUseCase() } returns flow {
            emit(
                PartialUser(
                    shippingAddress = "haven", fullName = "", emailAddress = "", phoneNumber = ""
                )
            )
        }

        val viewModel = CheckoutViewModel(
            fetchCartUseCase = fetchCartUseCase,
            checkoutCartUseCase = checkoutCartUseCase,
            getCachedUserUseCase = getCachedUserUseCase,
            getDeliveryFeeUseCase = fetchDeliveryFeeUseCase,
            getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
        )

        viewModel.uiState.test {
            val uiState = awaitItem()
            awaitComplete()

            Assert.assertEquals(mockCart, uiState.checkoutItems)
            Assert.assertEquals("haven", uiState.shippingAddress)
            Assert.assertEquals("$0.1", uiState.formattedTotalAmount)
        }
    }

    @Test
    fun testCheckout() = runTest {
        coEvery { checkoutCartUseCase() } just runs

        val viewModel = CheckoutViewModel(
            fetchCartUseCase = fetchCartUseCase,
            checkoutCartUseCase = checkoutCartUseCase,
            getCachedUserUseCase = getCachedUserUseCase,
            getDeliveryFeeUseCase = fetchDeliveryFeeUseCase,
            getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
        )

        val events = mutableListOf<Any>()

        val checkoutEventsJob =
            launch(dispatcher) {
                viewModel.checkoutCompleteEvent.toList(events)
            }

        viewModel.checkout()

        Assert.assertEquals(1, events.size)

        checkoutEventsJob.cancel()
    }

    @Test
    fun testSnackBarMessage() = runTest {
        coEvery { checkoutCartUseCase() } answers { throw Exception("ogoni") }

        val viewModel = CheckoutViewModel(
            fetchCartUseCase = fetchCartUseCase,
            checkoutCartUseCase = checkoutCartUseCase,
            getCachedUserUseCase = getCachedUserUseCase,
            getDeliveryFeeUseCase = fetchDeliveryFeeUseCase,
            getUserCurrencyPreferenceUseCase = getUserCurrencyPreferenceUseCase
        )

        val messages = mutableListOf<String>()

        val messageEventsJob =
            launch(dispatcher) {
                viewModel.snackBarMessage.toList(messages)
            }

        viewModel.checkout()

        Assert.assertEquals("ogoni", messages.first())

        messageEventsJob.cancel()
    }
}