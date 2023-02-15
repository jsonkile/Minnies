package com.demo.minnies.orders.presentation.screens.order

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.demo.minnies.database.models.OrderStatus
import com.demo.minnies.orders.domain.CancelOrderUseCase
import com.demo.minnies.orders.domain.ConfirmOrderUseCase
import com.demo.minnies.orders.domain.FetchOrderUseCase
import com.demo.minnies.orders.domain.FetchUserOrdersUseCase
import com.demo.minnies.orders.presentation.models.ViewOrder
import com.demo.minnies.orders.presentation.screens.home.OrdersViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class OrderViewModelTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var fetchOrderUseCase: FetchOrderUseCase

    @MockK(relaxed = true)
    lateinit var confirmOrderUseCase: ConfirmOrderUseCase

    @MockK(relaxed = true)
    lateinit var cancelOrderUseCase: CancelOrderUseCase

    val scheduler = TestCoroutineScheduler()

    @Test
    fun testSavedStateHandle() {
        val vm = OrderViewModel(SavedStateHandle(mapOf("ref" to "ref")), fetchOrderUseCase, cancelOrderUseCase, confirmOrderUseCase)
        Assert.assertTrue(vm.itemId == "ref")
    }

    @Test
    fun testUiState() = runTest {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(scheduler))

            val mockOrder = ViewOrder(status = OrderStatus.Ongoing, items = emptyList(), ref = "ref", totalAmount = "", id = 0, createdTime = "")

            coEvery { fetchOrderUseCase("ref") } returns flow { emit(mockOrder) }

            val viewModel = OrderViewModel(SavedStateHandle(mapOf("ref" to "ref")), fetchOrderUseCase, cancelOrderUseCase, confirmOrderUseCase)

            viewModel.uiState.test {
                Assert.assertTrue((awaitItem() as OrderViewModel.UiState.Success).order.ref == "ref")
            }

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun testCancelOrder() {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(scheduler))

            var step = 0

            coEvery { cancelOrderUseCase("ref") } answers { step++ }

            val viewModel = OrderViewModel(SavedStateHandle(mapOf("ref" to "ref")), fetchOrderUseCase, cancelOrderUseCase, confirmOrderUseCase)

            viewModel.cancelOrder()

            Assert.assertEquals(1, step)

        } finally {
            Dispatchers.resetMain()
        }
    }


    @Test
    fun testConfirmOrder() {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(scheduler))

            var step = 0

            coEvery { confirmOrderUseCase("ref") } answers { step++ }

            val viewModel = OrderViewModel(SavedStateHandle(mapOf("ref" to "ref")), fetchOrderUseCase, cancelOrderUseCase, confirmOrderUseCase)

            viewModel.confirmOrder()

            Assert.assertEquals(1, step)

        } finally {
            Dispatchers.resetMain()
        }
    }
}