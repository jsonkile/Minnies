package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.ui.MinniesTheme
import com.demo.minnies.shared.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shop.data.fakeShopItemsDataSet
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.presentation.models.ViewShopItem
import com.demo.minnies.shop.presentation.models.toView

const val SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG = "SHOP_SCREEN_FEATURED_ITEMS_LIST_TEST_TAG"
const val SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG =
    "SHOP_SCREEN_FEATURED_ITEMS_HEADING_TEST_TAG"

@Composable
fun ShopScreen(
    title: String,
    shopItemsByCategory: Map<Category, List<ViewShopItem>>,
    featuredItems: List<ViewShopItem>
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.Start
    ) {

        //Featured items
        if (featuredItems.isNotEmpty()) {
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
                items(items = featuredItems) { shopItem ->
                    ShopItemCard(viewShopItem = shopItem)
                }
            }
        }

        Category.values().forEach { category ->

            val categoryItems = shopItemsByCategory[category]

            categoryItems?.let { items ->

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
                    items(items = items) { shopItem ->
                        ShopItemCard(viewShopItem = shopItem)
                    }
                }

            }

        }

    }

}


@Composable
fun ShopScreenContainer(title: String, viewModel: ShopScreenViewModel) {
    val shopItemsByCategory =
        viewModel.shopItemsByCategories.collectAsState(initial = emptyMap()).value
    val featuredItems = viewModel.featuredItems.collectAsState(initial = emptyList()).value

    ShopScreen(
        title = title,
        shopItemsByCategory = shopItemsByCategory,
        featuredItems = featuredItems
    )
}


@Preview
@Composable
fun PreviewShopScreen() {
    val featuredItems = fakeShopItemsDataSet.filter { it.featured }.map { item -> item.toView() }
    val itemsByCategories =
        fakeShopItemsDataSet.map { item -> item.toView() }.groupBy {
            it.category
        }
    MinniesTheme {
        ShopScreen(title = "Shop", itemsByCategories, featuredItems)
    }
}