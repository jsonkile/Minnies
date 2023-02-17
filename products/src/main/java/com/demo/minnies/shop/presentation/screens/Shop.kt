package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.database.models.Category
import com.demo.minnies.shared.presentation.components.ScreenInfoView
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.GENERIC_ERROR_MESSAGE
import com.demo.minnies.shop.util.mockProducts
import com.demo.minnies.shop.util.toView

const val SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG = "SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG"
const val SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG =
    "SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG"

@Composable
fun Shop(viewModel: ShopViewModel, navigateToProduct: (Int) -> Unit) {
    val uiState = viewModel.uiState.collectAsState(initial = ShopViewModel.UiState.Loading).value

    ShopScreen(uiState, navigateToProduct)
}

@Composable
fun ShopScreen(
    uiState: ShopViewModel.UiState,
    navigateToProduct: (Int) -> Unit
) {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ShopViewModel.UiState.Error -> {
                val message =
                    uiState.throwable.message.orEmpty().ifEmpty { GENERIC_ERROR_MESSAGE }
                ScreenInfoView(
                    message = message,
                    icon = Icons.Default.HeartBroken,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            ShopViewModel.UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            is ShopViewModel.UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(bottom = 100.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    //show featured items sections if not empty
                    val showFeaturedSection =
                        remember(uiState.featured) { uiState.featured.isNotEmpty() }

                    if (showFeaturedSection) {
                        Text(
                            text = "Featured",
                            modifier = Modifier
                                .padding(
                                    start = PAGE_HORIZONTAL_MARGIN,
                                    top = 40.dp,
                                    bottom = 20.dp
                                )
                                .testTag(SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 17.sp
                            )
                        )

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_MARGIN),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.testTag(SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG)
                        ) {
                            items(items = uiState.featured, key = { it.id }) { shopItem ->
                                ProductCard(viewProduct = shopItem) { item ->
                                    navigateToProduct(item.id)
                                }
                            }
                        }
                    }

                    Category.values().forEach { category ->
                        val products = uiState.all[category].orEmpty()

                        if (products.isNotEmpty()) {
                            Text(
                                text = category.publicName,
                                modifier = Modifier.padding(
                                    start = PAGE_HORIZONTAL_MARGIN,
                                    top = 40.dp,
                                    bottom = 20.dp
                                ),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 17.sp
                                )
                            )

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_MARGIN),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(items = products, key = { it.id }) { product ->
                                    ProductCard(viewProduct = product) { item ->
                                        navigateToProduct(item.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewShopScreen() {
    val featuredItems =
        mockProducts.filter { it.featured }.map { item -> item.toView(Currency.USD) }
    val itemsByCategories =
        mockProducts.map { item -> item.toView(Currency.USD) }.groupBy { it.category }
    MinniesTheme {
        ShopScreen(
            uiState = ShopViewModel.UiState.Success(
                featured = featuredItems,
                all = itemsByCategories
            )
        ) {}
    }
}