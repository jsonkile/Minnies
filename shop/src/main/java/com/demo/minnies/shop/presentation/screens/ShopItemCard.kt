package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shop.data.models.Category
import com.demo.minnies.shop.presentation.models.ViewShopItem

@Composable
fun ShopItemCard(viewShopItem: ViewShopItem) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .wrapContentHeight()
    ) {
        Surface(shape = RoundedCornerShape(18.dp), modifier = Modifier.size(130.dp), color = Color.DarkGray) {

        }

        Text(
            text = viewShopItem.name,
            maxLines = 2,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(top = 3.dp)
        )

        Text(
            text = viewShopItem.price, maxLines = 1,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.LightGray
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Preview
@Composable
fun PreviewShopItemCard() {
    ShopItemCard(
        viewShopItem = ViewShopItem(
            "Nike Slides",
            "Made in Taiwan",
            "$3",
            "",
            listOf(1, 3),
            Category.Kicks
        )
    )
}