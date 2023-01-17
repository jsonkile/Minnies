package com.demo.minnies.shop.presentation.screens

import app.cash.turbine.test
import com.demo.minnies.shop.domain.usescases.SearchProductsUseCase
import com.demo.minnies.shop.presentation.models.ViewProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

internal class SearchViewModelTest {

    private val scheduler: TestCoroutineScheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val scope = TestScope(dispatcher)

    @Test
    fun `test that search term updates correctly`() = runTest {
        val searchProductsUseCase = object : SearchProductsUseCase {
            override fun invoke(term: String): Flow<List<ViewProduct>> = emptyFlow()
        }

        val searchViewModel = SearchViewModel(searchProductsUseCase)
        Assert.assertEquals(searchViewModel.searchTerm.value, "")
        searchViewModel.updateSearchTerm("test")
        Assert.assertEquals(searchViewModel.searchTerm.value, "test")
    }


    @Test
    fun `test that ui state moves from default to search results when search returns results`() =
        scope.runTest {
            Dispatchers.setMain(dispatcher)

            try {
                val searchProductsUseCase = object : SearchProductsUseCase {
                    override fun invoke(term: String): Flow<List<ViewProduct>> = flow {
                        emit(
                            listOf(
                                ViewProduct(name = "test-product")
                            )
                        )
                    }
                }

                val searchViewModel = SearchViewModel(searchProductsUseCase)
                searchViewModel.uiState.test {
                    searchViewModel.updateSearchTerm("test")
                    Assert.assertTrue(awaitItem() is SearchViewModel.UiState.Default)
                    val result = awaitItem() as SearchViewModel.UiState.SearchResults
                    Assert.assertEquals(result.results.first().name, "test-product")
                }
            } finally {
                Dispatchers.resetMain()
            }
        }


    @Test
    fun `test that ui state moves from default to empty state when search returns nothing`() =
        scope.runTest {
            Dispatchers.setMain(dispatcher)

            try {
                val searchProductsUseCase = object : SearchProductsUseCase {
                    override fun invoke(term: String): Flow<List<ViewProduct>> = flow {
                        emit(emptyList())
                    }
                }

                val searchViewModel = SearchViewModel(searchProductsUseCase)
                searchViewModel.uiState.test {
                    searchViewModel.updateSearchTerm("test")
                    Assert.assertTrue(awaitItem() is SearchViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is SearchViewModel.UiState.NoResults)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }


    @Test
    fun `test that ui state moves from default to error state when search returns error`() =
        scope.runTest {
            Dispatchers.setMain(dispatcher)

            try {
                val searchProductsUseCase = object : SearchProductsUseCase {
                    override fun invoke(term: String): Flow<List<ViewProduct>> = flow {
                        throw Exception()
                    }
                }

                val searchViewModel = SearchViewModel(searchProductsUseCase)
                searchViewModel.uiState.test {
                    searchViewModel.updateSearchTerm("test")
                    Assert.assertTrue(awaitItem() is SearchViewModel.UiState.Default)
                    Assert.assertTrue(awaitItem() is SearchViewModel.UiState.Error)
                }
            } finally {
                Dispatchers.resetMain()
            }
        }

}