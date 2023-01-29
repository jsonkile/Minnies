package com.demo.minnies.cart.presentation.screens.checkout

import com.demo.minnies.auth.domain.GetCachedUserUseCase
import com.demo.minnies.cart.domain.usecases.CheckoutCartUseCase
import com.demo.minnies.cart.domain.usecases.FetchCartUseCase
import com.demo.minnies.cart.presentation.screens.models.ViewCartItem
import com.demo.minnies.database.models.PartialUser
import io.mockk.coEvery
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

    val scheduler = TestCoroutineScheduler()
    val dispatcher = UnconfinedTestDispatcher(scheduler)

    @get:Rule
    val mockRule = MockKRule(this)

    @MockK
    lateinit var fetchCartUseCase: FetchCartUseCase

    @MockK
    lateinit var checkoutCartUseCase: CheckoutCartUseCase

    @MockK(relaxed = true)
    lateinit var getCachedUserUseCase: GetCachedUserUseCase

    @Test
    fun testLoadCart() = runTest {
        try {
            Dispatchers.setMain(dispatcher)

            val mockCart = listOf(
                ViewCartItem(
                    quantity = 1,
                    productId = 1,
                    id = 1,
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
                        shippingAddress = "haven",
                        fullName = "",
                        emailAddress = "",
                        phoneNumber = ""
                    )
                )
            }

            val viewModel = CheckoutViewModel(
                fetchCartUseCase = fetchCartUseCase,
                checkoutCartUseCase = checkoutCartUseCase,
                getCachedUserUseCase = getCachedUserUseCase
            )

            viewModel.checkout()

            Assert.assertEquals(mockCart, viewModel.uiState.value.checkoutItems)
            Assert.assertEquals("haven", viewModel.uiState.value.shippingAddress)
            Assert.assertEquals("$0.1", viewModel.uiState.value.formattedTotalAmount)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testCheckout() = runTest {
        try {
            Dispatchers.setMain(dispatcher)

            coEvery { checkoutCartUseCase() } just runs

            val viewModel = CheckoutViewModel(
                fetchCartUseCase = fetchCartUseCase,
                checkoutCartUseCase = checkoutCartUseCase,
                getCachedUserUseCase = getCachedUserUseCase
            )

            val events = mutableListOf<Any>()

            Assert.assertEquals(0, events.size)

            val checkoutEventsJob =
                launch(dispatcher) { viewModel.checkoutCompleteEvent.toList(events) }

            viewModel.checkout()

            Assert.assertEquals(1, events.size)

            checkoutEventsJob.cancel()
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testSnackBarMessage() = runTest {
        try {
            Dispatchers.setMain(dispatcher)

            coEvery { checkoutCartUseCase() } answers { throw Exception("ogoni") }

            val viewModel = CheckoutViewModel(
                fetchCartUseCase = fetchCartUseCase,
                checkoutCartUseCase = checkoutCartUseCase,
                getCachedUserUseCase = getCachedUserUseCase
            )

            val messages = mutableListOf<String>()

            Assert.assertEquals(0, messages.size)

            val messageEventsJob =
                launch(dispatcher) { viewModel.snackBarMessage.toList(messages) }

            viewModel.checkout()

            Assert.assertEquals("ogoni", messages.first())

            messageEventsJob.cancel()
        } finally {
            Dispatchers.resetMain()
        }
    }
}