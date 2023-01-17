package com.demo.minnies.shop.presentation.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.demo.minnies.shared.utils.NO_SEARCH_RESULTS
import com.demo.minnies.shared.utils.SEARCH_SCREEN_INTRO_LINE
import org.junit.Rule
import org.junit.Test

class SearchKtTest {

    @get:Rule
    val rule = createComposeRule()


    @Test
    fun whenUiState_IsLoading_ShowOnlyProgressBar() {
        rule.setContent {
            SearchScreen(
                uiState = SearchViewModel.UiState.Loading,
                searchTerm = "",
                onSearch = {},
                navigateToProduct = {})
        }

        rule.onNodeWithTag(SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG).assertIsDisplayed()
        rule.onNodeWithText(NO_SEARCH_RESULTS).assertDoesNotExist()
        rule.onNodeWithText(SEARCH_SCREEN_INTRO_LINE).assertDoesNotExist()
        rule.onNodeWithTag(SEARCH_SCREEN_RESULTS_LIST_TEST_TAG).assertDoesNotExist()
    }


    @Test
    fun whenUiState_IsSearchResults_ShowOnlyItemsList() {
        rule.setContent {
            SearchScreen(
                uiState = SearchViewModel.UiState.SearchResults(results = listOf()),
                searchTerm = "",
                onSearch = {},
                navigateToProduct = {})
        }

        rule.onNodeWithTag(SEARCH_SCREEN_RESULTS_LIST_TEST_TAG).assertIsDisplayed()
        rule.onNodeWithTag(SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG).assertDoesNotExist()
        rule.onNodeWithText(NO_SEARCH_RESULTS).assertDoesNotExist()
        rule.onNodeWithText(SEARCH_SCREEN_INTRO_LINE).assertDoesNotExist()
    }


    @Test
    fun whenUiState_IsDefault_ShowIntroLine() {
        rule.setContent {
            SearchScreen(
                uiState = SearchViewModel.UiState.Default,
                searchTerm = "",
                onSearch = {},
                navigateToProduct = {})
        }

        rule.onNodeWithTag(SEARCH_SCREEN_RESULTS_LIST_TEST_TAG).assertDoesNotExist()
        rule.onNodeWithTag(SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG).assertDoesNotExist()
        rule.onNodeWithText(NO_SEARCH_RESULTS).assertDoesNotExist()
        rule.onNodeWithText(SEARCH_SCREEN_INTRO_LINE).assertIsDisplayed()
    }


    @Test
    fun whenUiState_IsError_ShowErrorMessage() {
        rule.setContent {
            SearchScreen(
                uiState = SearchViewModel.UiState.Error(throwable = Throwable(message = "bad robot")),
                searchTerm = "",
                onSearch = {},
                navigateToProduct = {})
        }

        rule.onNodeWithTag(SEARCH_SCREEN_RESULTS_LIST_TEST_TAG).assertDoesNotExist()
        rule.onNodeWithTag(SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG).assertDoesNotExist()
        rule.onNodeWithText(NO_SEARCH_RESULTS).assertDoesNotExist()
        rule.onNodeWithText(SEARCH_SCREEN_INTRO_LINE).assertDoesNotExist()
        rule.onNodeWithText("bad robot").assertIsDisplayed()
    }

}