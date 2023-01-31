package com.demo.minnies.orders.presentation.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.orders.presentation.models.OrderContent

@Composable
fun OrderContentComposable(
    orderContent: OrderContent,
    modifier: Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (image, name, priceAndQuantity, amount) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(orderContent.productImage)
                .crossfade(true)
                .build(),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.DarkGray)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = orderContent.productName,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(image.top)
                start.linkTo(image.end, 10.dp)
                end.linkTo(parent.end)
                bottom.linkTo(priceAndQuantity.top)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "${orderContent.amount} x ${orderContent.quantity}",
            modifier = Modifier.constrainAs(priceAndQuantity) {
                top.linkTo(name.bottom)
                bottom.linkTo(image.bottom)
                start.linkTo(name.start)
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = orderContent.amount,
            modifier = Modifier.constrainAs(amount) {
                top.linkTo(priceAndQuantity.top)
                bottom.linkTo(priceAndQuantity.bottom)
                end.linkTo(parent.end)
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun PreviewCheckoutItem() {
    OrderContentComposable(
        orderContent = OrderContent(
            amount = "$800",
            productId = 0,
            productImage = "",
            productName = "Joggers",
            quantity = 2,
        ), modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}