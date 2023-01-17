package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.database.room.models.Category
import com.demo.minnies.shop.presentation.models.ViewProduct

const val SHOP_ITEM_CARD_TEST_TAG = "SHOP_ITEM_CARD_TEST_TAG"
const val SHOP_ITEM_RATING_TEST_TAG = "SHOP_ITEM_RATING_TEST_TAG"
const val SHOP_ITEM_RATING_ICON_TEST_TAG = "SHOP_ITEM_RATING_ICON_TEST_TAG"

@Composable
fun ProductCard(viewProduct: ViewProduct, clickAction: ((ViewProduct) -> Unit)) {

    ConstraintLayout(modifier = Modifier
        .width(130.dp)
        .wrapContentHeight()
        .clickable {
            clickAction(viewProduct)
        }
        .testTag(SHOP_ITEM_CARD_TEST_TAG)) {

        val (image, title, price, rating, ratingIcon) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewProduct.image)
                .crossfade(true)
                .build(),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color = Color.DarkGray)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = viewProduct.name,
            maxLines = 1,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(end = 2.dp)
                .constrainAs(title) {
                    top.linkTo(image.bottom, 2.dp)
                    start.linkTo(image.start)
                    end.linkTo(image.end)

                    width = Dimension.fillToConstraints
                },
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = viewProduct.formattedPrice, maxLines = 1,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.LightGray
            ),
            modifier = Modifier
                .constrainAs(price) {
                    top.linkTo(title.bottom, 2.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(rating.start, 10.dp)
                    start.linkTo(image.start)
                    width = Dimension.fillToConstraints
                }
        )

        val showRating = remember { viewProduct.rating in 1.0..5.0 }

        if (showRating) {

            Text(
                text = viewProduct.rating.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    color = Color.LightGray
                ),
                modifier = Modifier
                    .constrainAs(rating) {
                        top.linkTo(ratingIcon.top)
                        bottom.linkTo(ratingIcon.bottom)
                        end.linkTo(ratingIcon.start, 1.dp)
                    }
                    .testTag(SHOP_ITEM_RATING_TEST_TAG)
            )

            Image(
                imageVector = Icons.Default.Star,
                contentDescription = "rating icon",
                modifier = Modifier
                    .size(8.dp)
                    .constrainAs(ratingIcon) {
                        top.linkTo(price.top)
                        bottom.linkTo(price.bottom)
                        end.linkTo(image.end)
                    }
                    .testTag(SHOP_ITEM_RATING_ICON_TEST_TAG),
                colorFilter = ColorFilter.tint(color = Color.LightGray)
            )

        }
    }
}


@Preview
@Composable
fun ProductCard() {
    ProductCard(
        viewProduct = ViewProduct(
            0,
            "Balanciaga Boomers",
            "Made in Taiwan",
            price = 0.0,
            formattedPrice = "$3",
            "",
            listOf(1, 3),
            Category.Kicks,
            false,
            4.5
        )
    ) {}
}