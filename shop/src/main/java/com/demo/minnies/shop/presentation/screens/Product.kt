package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.shared.presentation.components.DefaultButton
import com.demo.minnies.shared.presentation.components.ErrorView
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shop.presentation.models.ViewProduct

const val LOADING_TEST_TAG = "LOADING_TEST_TAG"
const val PRODUCT_DETAILS_AREA_TEST_TAG = "PRODUCT_DETAILS_AREA_TEST_TAG"
const val ERROR_AREA_TEST_TAG = "ERROR_AREA_TEST_TAG"
const val ADD_TO_CART_BUTTON_TEST_TAG = "ADD_TO_CART_BUTTON_TEST_TAG"
const val PRICE_TEST_TAG = "PRICE_TEST_TAG"

@Composable
fun Product(viewModel: ProductViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsState(initial = ProductViewModel.UiState.Empty).value
    val snackBarHostState = remember { SnackbarHostState() }

    ProductScreen(uiState = uiState)

    LaunchedEffect(uiState) {
        if (uiState is ProductViewModel.UiState.Error) {
            val message = uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
            snackBarHostState.showSnackbar(message = message)
        }
    }
}


@Composable
fun ProductScreen(uiState: ProductViewModel.UiState) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            ProductViewModel.UiState.Empty -> {}

            is ProductViewModel.UiState.Error -> {
                val message =
                    uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
                ErrorView(
                    message = message,
                    icon = Icons.Default.HeartBroken,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(ERROR_AREA_TEST_TAG)
                )
            }

            ProductViewModel.UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(LOADING_TEST_TAG)
                )
            }

            is ProductViewModel.UiState.Success -> {

                val product = uiState.product

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
                        .verticalScroll(scrollState)
                        .testTag(PRODUCT_DETAILS_AREA_TEST_TAG)
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "product image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(280.dp)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(color = Color.DarkGray)

                    )

                    Text(
                        text = product.name,
                        modifier = Modifier,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = product.price,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 5.dp)
                            .testTag(PRICE_TEST_TAG),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    Text(
                        text = "*Free Shipping Worldwide",
                        modifier = Modifier.padding(top = 1.dp, bottom = 5.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
                    )

                    DefaultButton(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 5.dp)
                            .testTag(ADD_TO_CART_BUTTON_TEST_TAG),
                        text = "Add to Cart",
                        icon = Icons.Outlined.AddShoppingCart
                    ) {

                    }

                    Text(
                        text = product.description,
                        modifier = Modifier.padding(vertical = 5.dp),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewProductScreen() {
    MinniesTheme {
        ProductScreen(
            ProductViewModel.UiState.Success(
                ViewProduct(
                    name = "Island Prime Trouser/Nikkas",
                    price = "$8.9",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum rhoncus sagittis eros, nec dignissim libero ornare quis. Nulla eu eros massa. Donec auctor, lacus a rutrum dapibus, dui justo imperdiet ligula, non aliquet est lorem ut libero. Aenean facilisis sed ligula nec finibus. Ut et leo ultrices enim imperdiet dapibus. Integer id ligula vel velit ornare imperdiet. Mauris tristique, mi sit amet efficitur pretium, sem tortor vestibulum dui, vel faucibus est odio euismod est. In venenatis in nisi id mollis. Proin ornare arcu a enim sodales, et ullamcorper velit vulputate. Sed eget erat turpis. Donec sodales urna elit. Aliquam vel libero convallis neque porta sodales nec ac tellus. Curabitur commodo scelerisque dolor sed pharetra."
                )
            )
        )
    }
}

@Preview
@Composable
fun PreviewProductScreenWithError() {
    MinniesTheme {
        ProductScreen(uiState = ProductViewModel.UiState.Error(Throwable(message = "Something went wrong!")))
    }
}


@Preview
@Composable
fun PreviewProductScreenLoading() {
    MinniesTheme {
        ProductScreen(uiState = ProductViewModel.UiState.Loading)
    }
}