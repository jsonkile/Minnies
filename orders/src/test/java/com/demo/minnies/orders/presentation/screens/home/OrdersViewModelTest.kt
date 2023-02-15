package com.demo.minnies.orders.presentation.screens.home

import app.cash.turbine.test
import com.demo.minnies.orders.domain.FetchUserOrdersUseCase
import com.demo.minnies.orders.presentation.models.ViewOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

internal class OrdersViewModelTest {

    val scheduler = TestCoroutineScheduler()

    @Test
    fun testUiState() = runTest {
        try {
            Dispatchers.setMain(UnconfinedTestDispatcher(scheduler))

            val mockList = listOf(
                ViewOrder(
                    id = 0,
                    ref = "ref",
                    items = emptyList(),
                    createdTime = "",
                    totalAmount = ""
                ),
                ViewOrder(
                    id = 0,
                    ref = "ref",
                    items = emptyList(),
                    createdTime = "",
                    totalAmount = ""
                )
            )

            val fetchUserOrdersUseCase = object : FetchUserOrdersUseCase {
                override fun invoke(): Flow<List<ViewOrder>> = flow { emit(mockList) }
            }

            val viewModel = OrdersViewModel(fetchUserOrdersUseCase)

            viewModel.uiState.test {
                Assert.assertTrue((awaitItem() as OrdersViewModel.UiState.Success).orders == mockList)
            }

        }finally {
            Dispatchers.resetMain()
        }
    }
}