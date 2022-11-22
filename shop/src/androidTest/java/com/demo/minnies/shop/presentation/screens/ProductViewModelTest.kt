package com.demo.minnies.shop.presentation.screens

import androidx.lifecycle.SavedStateHandle
import com.demo.minnies.shared.utils.CustomExceptions
import com.demo.minnies.shop.domain.usescases.FetchProductByIdUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
internal class ProductViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: ProductViewModel

    @Inject
    lateinit var fetchProductByIdUseCase: FetchProductByIdUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun uiState_IsLoading_Initially() = runTest {
        viewModel =
            ProductViewModel(
                savedStateHandle = SavedStateHandle(mapOf("id" to 4)),
                fetchProductByIdUseCase
            )
        Assert.assertTrue(viewModel.uiState.first() is ProductViewModel.UiState.Loading)
    }

    @Test
    fun uiState_IsSuccessAndReturnsCorrectDataWhen_IdExists() = runTest {
        viewModel =
            ProductViewModel(
                savedStateHandle = SavedStateHandle(mapOf("id" to 4)),
                fetchProductByIdUseCase
            )
        Assert.assertTrue(viewModel.uiState.drop(1).first() is ProductViewModel.UiState.Success)
        Assert.assertEquals(
            "Vans Retro",
            (viewModel.uiState.drop(1).first() as ProductViewModel.UiState.Success).product.name
        )
    }

    @Test
    fun uiState_IsError_WhenIdDoesNotExists() = runTest {
        viewModel =
            ProductViewModel(
                savedStateHandle = SavedStateHandle(mapOf("id" to 104)),
                fetchProductByIdUseCase
            )
        Assert.assertTrue(viewModel.uiState.drop(1).first() is ProductViewModel.UiState.Error)
        Assert.assertTrue(
            (viewModel.uiState.drop(1)
                .first() as ProductViewModel.UiState.Error).throwable is CustomExceptions.NotFoundException
        )
    }
}