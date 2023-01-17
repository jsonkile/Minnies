package com.demo.minnies.shop.presentation.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.demo.minnies.shared.presentation.ui.MinniesTheme

@Composable
fun CheckoutSummary(
    modifier: Modifier,
    totalCartCount: Int,
    totalCheckoutAmount: String,
    shippingAddress: String,
    updateBillingAddressAction: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (totalPrice, totalPriceLabel, address, updateAddressControl, addressLabel, paymentMethodLabel, paymentMethod, deliveryCostLabel, deliveryCost) = createRefs()

        Text(
            text = "Total amount", modifier = Modifier.constrainAs(totalPriceLabel) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }, fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        )

        Text(
            text = totalCheckoutAmount,
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(totalPrice) {
                end.linkTo(parent.end)
                top.linkTo(totalPriceLabel.top)
                bottom.linkTo(totalPriceLabel.bottom)
                start.linkTo(totalPriceLabel.end, 20.dp)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp
        )


        Text(
            text = "Delivery fee", modifier = Modifier.constrainAs(deliveryCostLabel) {
                start.linkTo(totalPriceLabel.start)
                top.linkTo(totalPriceLabel.bottom, 10.dp)
            }, fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        )

        Text(
            text = "${totalCheckoutAmount.getOrNull(0) ?: "$"}0",
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(deliveryCost) {
                end.linkTo(parent.end)
                top.linkTo(deliveryCostLabel.top)
                bottom.linkTo(deliveryCostLabel.bottom)
                start.linkTo(deliveryCostLabel.end, 20.dp)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp
        )


        Text(
            text = "Shipping address",
            modifier = Modifier.constrainAs(addressLabel) {
                top.linkTo(deliveryCostLabel.bottom, 10.dp)
                start.linkTo(totalPriceLabel.start)
            },
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic
        )


        Text(
            text = shippingAddress,
            modifier = Modifier.constrainAs(address) {
                top.linkTo(addressLabel.bottom, 10.dp)
                start.linkTo(totalPriceLabel.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(updateAddressControl.start)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 13.sp
        )


        Image(
            imageVector = Icons.Default.Edit,
            contentDescription = "Change address",
            modifier = Modifier
                .constrainAs(updateAddressControl) {
                    top.linkTo(address.top)
                    bottom.linkTo(address.bottom)
                    end.linkTo(parent.end)
                }
                .size(25.dp)
                .clickable(
                    indication = rememberRipple(
                        bounded = false,
                        radius = 15.dp,
                        color = MaterialTheme.colorScheme.inversePrimary
                    ),
                    interactionSource = MutableInteractionSource()
                ) {
                    updateBillingAddressAction()
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}

@Preview
@Composable
fun PreviewCheckoutSummary() {
    MinniesTheme {
        CheckoutSummary(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            totalCartCount = 3,
            totalCheckoutAmount = "N3,000",
            shippingAddress = "Eastern by pass"
        ) {

        }
    }
}