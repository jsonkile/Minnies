package com.demo.minnies.orders.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.orders.presentation.models.OrderContent

@Composable
fun OrderContentsContainer(contents: List<OrderContent>, modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(color = Color.LightGray, width = 0.2.dp)
    ) {
        if (contents.size == 1) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contents.first().productImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = Color.LightGray)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier,
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(2.dp)
            ) {
                itemsIndexed(contents.take(6)) { index, content ->
                    if (contents.size > 6 && index == 5) {
                        Box(
                            Modifier
                                .size(25.dp)
                                .background(
                                    shape = RoundedCornerShape(3.dp),
                                    color = Color.LightGray
                                ), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+1",
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(content.productImage)
                                .crossfade(true)
                                .build(),
                            contentDescription = "product image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(color = Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderContentsContainer() {
    Box(
        Modifier
            .padding(30.dp)
            .wrapContentSize()
    ) {
        OrderContentsContainer(
            listOf(
                OrderContent(
                    amount = "",
                    productImage = "",
                    productName = "",
                    productId = 1,
                    quantity = 1
                ),
                OrderContent(
                    amount = "",
                    productImage = "",
                    productName = "",
                    productId = 1,
                    quantity = 1
                ),
                OrderContent(
                    amount = "",
                    productImage = "",
                    productName = "",
                    productId = 1,
                    quantity = 1
                ),
//                OrderContent(
//                    amount = "",
//                    productImage = "",
//                    productName = "",
//                    productId = 1,
//                    quantity = 1
//                ),
//                OrderContent(
//                    amount = "",
//                    productImage = "",
//                    productName = "",
//                    productId = 1,
//                    quantity = 1
//                )
            ),
            Modifier
                .requiredSize(58.dp)
        )
    }
}


@Composable
fun RoundedOrderContentsContainer(contents: List<OrderContent>, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-10).dp, Alignment.End)
    ) {
        contents.take(6).forEachIndexed { index, content ->
            if (contents.size > 6 && index == 5) {
                Box(
                    Modifier
                        .size(30.dp)
                        .background(
                            shape = CircleShape,
                            color = Color.LightGray
                        )
                        .border(width = .5.dp, color = Color.DarkGray, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "+1",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(content.productImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray, shape = CircleShape)
                        .border(width = .5.dp, color = Color.DarkGray, shape = CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrderContentsRoundContainer() {
    RoundedOrderContentsContainer(
        listOf(
            OrderContent(
                amount = "",
                productImage = "",
                productName = "",
                productId = 1,
                quantity = 1
            ),
            OrderContent(
                amount = "",
                productImage = "",
                productName = "",
                productId = 1,
                quantity = 1
            ),
            OrderContent(
                amount = "",
                productImage = "",
                productName = "",
                productId = 1,
                quantity = 1
            ),
            OrderContent(
                amount = "",
                productImage = "",
                productName = "",
                productId = 1,
                quantity = 1
            ),
            OrderContent(
                amount = "",
                productImage = "",
                productName = "",
                productId = 1,
                quantity = 1
            )
        ),
        Modifier
            .wrapContentSize()
    )
}