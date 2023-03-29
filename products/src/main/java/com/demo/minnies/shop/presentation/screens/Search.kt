@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.shared.presentation.components.ScreenInfoView
import com.demo.minnies.shared.presentation.components.PageInfo
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.NO_SEARCH_RESULTS
import com.demo.minnies.shared.utils.SEARCH_SCREEN_INTRO_LINE

const val SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG = "SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG"
const val SEARCH_SCREEN_RESULTS_LIST_TEST_TAG = "SEARCH_SCREEN_RESULTS_LIST_TEST_TAG"

@Composable
fun Search(viewModel: SearchViewModel = hiltViewModel(), navigateToProduct: (Int) -> Unit) {
    val uiState = viewModel.uiState.collectAsState(initial = SearchViewModel.UiState.Default)

    val searchTerm = viewModel.searchTerm.collectAsState()

    SearchScreen(uiState = uiState.value, onSearch = {
        viewModel.updateSearchTerm(it)
    }, searchTerm = searchTerm.value, navigateToProduct = navigateToProduct)
}


@Composable
fun SearchScreen(
    uiState: SearchViewModel.UiState,
    searchTerm: String,
    onSearch: (String) -> Unit,
    navigateToProduct: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
            .semantics { testTagsAsResourceId = true }
    ) {

        val (searchBox, resultsArea, loadingIndicator, searchInfo) = createRefs()

        SearchBox(
            term = searchTerm,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(searchBox) {
                    top.linkTo(parent.top, 15.dp)
                }
                .testTag("search_bar_test_tag"), onSearch = onSearch
        )

        when (uiState) {
            SearchViewModel.UiState.Default -> {
                PageInfo(
                    message = SEARCH_SCREEN_INTRO_LINE,
                    icon = Icons.TwoTone.Search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(searchInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    contentDescription = "search"
                )
            }

            is SearchViewModel.UiState.Error -> {
                ScreenInfoView(
                    message = uiState.throwable.message.orEmpty(),
                    icon = Icons.TwoTone.SearchOff,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(searchInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    showAsError = true
                )
            }

            SearchViewModel.UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(loadingIndicator) {
                            top.linkTo(searchBox.bottom)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                        .testTag(SEARCH_SCREEN_LOADING_INDICATOR_TEST_TAG)
                )
            }

            is SearchViewModel.UiState.SearchResults -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(resultsArea) {
                            top.linkTo(searchBox.bottom)
                        }
                        .testTag(SEARCH_SCREEN_RESULTS_LIST_TEST_TAG),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(top = 20.dp, bottom = 100.dp)
                ) {

                    items(items = uiState.results) { product ->
                        ProductCard(
                            viewProduct = product,
                            modifier = Modifier
                                .width(130.dp)
                                .wrapContentHeight()
                        ) { item ->
                            navigateToProduct(item.id)
                        }
                    }

                }

            }

            SearchViewModel.UiState.NoResults -> {
                ScreenInfoView(
                    message = "$NO_SEARCH_RESULTS for `$searchTerm`",
                    icon = Icons.TwoTone.SearchOff,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(searchInfo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    showAsError = true
                )
            }
        }

    }
}


@Preview
@Composable
fun PreviewSearchScreen() {
    Column(modifier = Modifier.wrapContentSize()) {
        SearchScreen(
            searchTerm = "",
            uiState = SearchViewModel.UiState.Loading,
            onSearch = {},
            navigateToProduct = {})

        SearchScreen(
            searchTerm = "",
            uiState = SearchViewModel.UiState.Loading,
            onSearch = {},
            navigateToProduct = {})
    }
}


@Composable
fun SearchBox(term: String = "", modifier: Modifier, onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue(text = term)) }

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "search icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(text.text)
                keyboardController?.hide()
            }, onGo = {
                this.defaultKeyboardAction(ImeAction.Search)
                onSearch(text.text)
                keyboardController?.hide()
            }, onSend = {
                onSearch(text.text)
                keyboardController?.hide()
            },
            onDone = {
                onSearch(text.text)
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        maxLines = 1
    )

    LaunchedEffect(true) {
        keyboardController?.show()
    }
}

@Preview
@Composable
fun PreviewSearchBox() {
    MaterialTheme {
        SearchBox(
            modifier = Modifier.padding(horizontal = PAGE_HORIZONTAL_MARGIN),
            term = "Search",
            onSearch = {})
    }
}