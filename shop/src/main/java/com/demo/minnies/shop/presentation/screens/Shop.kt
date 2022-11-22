package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shop.data.fakeProductsDataSets
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.presentation.models.toView

const val SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG = "SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG"
const val SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG =
    "SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG"

@Composable
fun Shop(
    title: String,
    viewModel: ShopScreenViewModel,
    navigateToProduct: (Int) -> Unit
) {

    val featuredProducts = viewModel.featuredItems.collectAsState(initial = emptyList()).value

    val allProducts = viewModel.allItems.collectAsState(initial = emptyMap()).value

    ShopScreen(
        title = title,
        featuredItems = featuredProducts,
        allItems = allProducts,
        navigateToProduct
    )
}

@Composable
fun ShopScreen(
    title: String,
    featuredItems: List<ViewProduct>,
    allItems: Map<Category, List<ViewProduct>>,
    navigateToProduct: (Int) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.Start
    ) {

        //show featured items sections if not empty
        val showFeaturedSection = remember(featuredItems) { featuredItems.isNotEmpty() }

        if (showFeaturedSection) {
            Text(
                text = "Featured",
                modifier = Modifier
                    .padding(start = PAGE_HORIZONTAL_MARGIN, top = 40.dp, bottom = 20.dp)
                    .testTag(SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    fontSize = 17.sp
                )
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_MARGIN),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.testTag(SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG)
            ) {
                items(items = featuredItems, key = { it.id }) { shopItem ->
                    ProductCard(viewProduct = shopItem) { item ->
                        navigateToProduct(item.id)
                    }
                }
            }
        }

        Category.values().forEach { category ->
            val products = allItems[category].orEmpty()

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
                        color = Color.LightGray,
                        fontSize = 17.sp
                    )
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = PAGE_HORIZONTAL_MARGIN),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = products, key = { it.id }) { shopItem ->
                        ProductCard(viewProduct = shopItem) { item ->
                            navigateToProduct(item.id)
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
    val featuredItems = fakeProductsDataSets.filter { it.featured }.map { item -> item.toView() }
    val itemsByCategories =
        fakeProductsDataSets.map { item -> item.toView() }.groupBy { it.category }
    MinniesTheme {
        ShopScreen(title = "Shop", featuredItems, itemsByCategories) {}
    }
}