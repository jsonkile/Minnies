package com.demo.minnies.shop.presentation.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shop.presentation.models.ViewCartItem

@Composable
fun CartItem(
    viewCartItem: ViewCartItem,
    modifier: Modifier,
    updateQuantityAction: (ViewCartItem, Int) -> Unit,
    imageClickAction: (Long) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (image, name, minusButton, plusButton, quantity, amount) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewCartItem.productImage)
                .crossfade(true)
                .build(),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.DarkGray)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .clickable {
                    imageClickAction(viewCartItem.id)
                }
        )

        Text(
            text = viewCartItem.productName,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(image.top)
                start.linkTo(image.end, 10.dp)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )

        Image(
            imageVector = Icons.Default.RemoveCircle,
            contentDescription = "remove from quantity",
            modifier = Modifier
                .size(25.dp)
                .constrainAs(minusButton) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(image.bottom)
                    start.linkTo(name.start)
                }
                .then(
                    if (viewCartItem.quantity > 1) {
                        Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(bounded = false)
                        ) {
                            //reduce quantity
                            updateQuantityAction(viewCartItem, -1)
                        }
                    } else Modifier
                ),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        )

        Text(
            text = "${viewCartItem.quantity}",
            modifier = Modifier.constrainAs(quantity) {
                top.linkTo(minusButton.top)
                bottom.linkTo(minusButton.bottom)
                start.linkTo(minusButton.end, 10.dp)
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = viewCartItem.formattedTotalAmount,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(amount) {
                top.linkTo(minusButton.top)
                bottom.linkTo(minusButton.bottom)
                end.linkTo(parent.end, 10.dp)
            }, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        )

        Image(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "add to quantity",
            modifier = Modifier
                .size(25.dp)
                .constrainAs(plusButton) {
                    top.linkTo(minusButton.top)
                    bottom.linkTo(minusButton.bottom)
                    start.linkTo(quantity.end, 10.dp)
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = false)
                ) {
                    //add quantity
                    updateQuantityAction(viewCartItem, 1)
                },
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview
@Composable
fun PreviewCartItem() {
    CartItem(
        viewCartItem = ViewCartItem(
            id = 0,
            formattedProductPrice = "$400",
            formattedTotalAmount = "$800",
            productId = 0,
            productImage = "",
            productName = "Joggers",
            baseProductPrice = 5.0,
            quantity = 2,
            currency = Currency.EUR
        ), modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        { _, _ -> }, imageClickAction = {}
    )
}