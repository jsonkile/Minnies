package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.ui.MinniesTheme
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.domain.usescases.FetchAndGroupShopItemsUseCase
import com.demo.minnies.shop.presentation.models.ViewShopItem
import com.demo.minnies.shop.util.forView

@Composable
fun ShopScreen(title: String, shopItems: Map<Category, List<ViewShopItem>>) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.Start
    ) {
        val tops = shopItems[Category.Top]

        tops?.let {

            Text(
                text = Category.Top.publicName,
                modifier = Modifier.padding(start = 22.dp, top = 40.dp, bottom = 20.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    fontSize = 17.sp
                )
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = tops) { top ->
                    ShopItemCard(viewShopItem = top)
                }
            }

        }

        val kicks = shopItems[Category.Kicks]

        kicks?.let {

            Text(
                text = Category.Kicks.publicName,
                modifier = Modifier.padding(start = 22.dp, top = 40.dp, bottom = 20.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    fontSize = 17.sp
                )
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = kicks) { top ->
                    ShopItemCard(viewShopItem = top)
                }
            }

        }
    }

}


@Preview
@Composable
fun PreviewShopScreen() {
    val mockList = FetchAndGroupShopItemsUseCase.mockList
    val shopItems = mockList.map { item -> item.forView() }.groupBy {
        it.category
    }
    MinniesTheme {
        ShopScreen(title = "Shop", shopItems)
    }
}