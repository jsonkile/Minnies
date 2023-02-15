@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)

package com.demo.minnies.shop.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.components.ErrorView
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPrice
import com.demo.minnies.shop.presentation.models.ViewProduct
import com.demo.minnies.shop.util.ADDED_T0_CART_MESSAGE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val LOADING_TEST_TAG = "LOADING_TEST_TAG"
const val PRODUCT_DETAILS_AREA_TEST_TAG = "PRODUCT_DETAILS_AREA_TEST_TAG"
const val ERROR_AREA_TEST_TAG = "ERROR_AREA_TEST_TAG"
const val ADD_TO_CART_BUTTON_TEST_TAG = "ADD_TO_CART_BUTTON_TEST_TAG"
const val PRICE_TEST_TAG = "PRICE_TEST_TAG"

@Composable
fun Product(viewModel: ProductViewModel = hiltViewModel(), gotoCartAction: () -> Unit) {

    val uiState = viewModel.uiState.collectAsState(initial = ProductViewModel.UiState.Empty).value
    val scaffoldState = rememberScaffoldState()

    ProductScreen(uiState = uiState, addToCartAction = { productId, quantity, userId ->
        viewModel.addToCart(productId, quantity, userId)
    }, scaffoldState = scaffoldState)

    LaunchedEffect(uiState) {
        if (uiState is ProductViewModel.UiState.Error) {
            val message = uiState.throwable.message.orEmpty().ifEmpty { "Something went wrong!" }
            scaffoldState.snackbarHostState.showSnackbar(
                message,
                duration = SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(viewModel.addedToCartEvent) {
        viewModel.addedToCartEvent.collectLatest {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = ADDED_T0_CART_MESSAGE,
                duration = SnackbarDuration.Short,
                actionLabel = "Go to Cart"
            )

            when (result) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> {
                    gotoCartAction()
                }
            }
        }
    }
}


@Composable
fun ProductScreen(
    uiState: ProductViewModel.UiState,
    scaffoldState: ScaffoldState,
    addToCartAction: (Int, Int, Long?) -> Unit = { _, _, _ -> }
) {
    val scrollState = rememberScrollState()

    val addToCartBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded })

    val coroutineScope = rememberCoroutineScope()

    BackHandler(addToCartBottomSheetState.isVisible) {
        coroutineScope.launch {
            addToCartBottomSheetState.hide()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Box {
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

                    ModalBottomSheetLayout(
                        sheetContent = {
                            AddToCartBottomSheet(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(color = MaterialTheme.colorScheme.background)
                                    .padding(horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 15.dp),
                                uiState = uiState,
                                addToCartAction = addToCartAction,
                                dismissModalAction = {
                                    coroutineScope.launch {
                                        addToCartBottomSheetState.hide()
                                    }
                                }
                            )
                        },
                        sheetState = addToCartBottomSheetState,
                        sheetElevation = 19.dp
                    ) {

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
                                text = product.formattedPrice,
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

                            MinniesDefaultButton(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(vertical = 10.dp)
                                    .testTag(ADD_TO_CART_BUTTON_TEST_TAG),
                                text = "Add to cart",
                                icon = Icons.Outlined.AddShoppingCart
                            ) {
                                coroutineScope.launch {
                                    addToCartBottomSheetState.show()
                                }
                            }

                            Text(
                                text = product.description,
                                modifier = Modifier.padding(vertical = 5.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                                lineHeight = 28.sp
                            )
                        }
                    }
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
                    formattedPrice = "$8.9",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum rhoncus sagittis eros, nec dignissim libero ornare quis. Nulla eu eros massa. Donec auctor, lacus a rutrum dapibus, dui justo imperdiet ligula, non aliquet est lorem ut libero. Aenean facilisis sed ligula nec finibus. Ut et leo ultrices enim imperdiet dapibus. Integer id ligula vel velit ornare imperdiet. Mauris tristique, mi sit amet efficitur pretium, sem tortor vestibulum dui, vel faucibus est odio euismod est. In venenatis in nisi id mollis. Proin ornare arcu a enim sodales, et ullamcorper velit vulputate. Sed eget erat turpis. Donec sodales urna elit. Aliquam vel libero convallis neque porta sodales nec ac tellus. Curabitur commodo scelerisque dolor sed pharetra."
                ), currency = Currency.USD
            ), addToCartAction = { _, _, _ -> }, scaffoldState = rememberScaffoldState()
        )
    }
}

@Preview
@Composable
fun PreviewProductScreenWithError() {
    MinniesTheme {
        ProductScreen(
            uiState = ProductViewModel.UiState.Error(Throwable(message = "Something went wrong!")),
            scaffoldState = rememberScaffoldState()
        ) { _, _, _ -> }
    }
}


@Preview
@Composable
fun PreviewProductScreenLoading() {
    MinniesTheme {
        ProductScreen(
            uiState = ProductViewModel.UiState.Loading,
            scaffoldState = rememberScaffoldState()
        ) { _, _, _ -> }
    }
}


@Composable
fun AddToCartBottomSheet(
    modifier: Modifier,
    uiState: ProductViewModel.UiState.Success,
    dismissModalAction: () -> Unit,
    addToCartAction: (Int, Int, Long?) -> Unit
) {

    val product = uiState.product

    ConstraintLayout(modifier = modifier) {
        val (image, name, addToCartButton, minusButton, plusButton, quantity, amount) = createRefs()

        var quantityCount by remember { mutableStateOf(1) }
        val totalAmount = product.price * quantityCount

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
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
        )

        Text(
            text = product.name,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(image.top)
                start.linkTo(image.end, 10.dp)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Image(
            imageVector = Icons.Default.RemoveCircle,
            contentDescription = "remove from quantity",
            modifier = Modifier
                .size(32.dp)
                .constrainAs(minusButton) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(image.bottom)
                    start.linkTo(name.start)
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = false)
                ) {
                    if (quantityCount > 1) quantityCount--
                },
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        )

        Text(
            text = "$quantityCount",
            modifier = Modifier.constrainAs(quantity) {
                top.linkTo(minusButton.top)
                bottom.linkTo(minusButton.bottom)
                start.linkTo(minusButton.end, 10.dp)
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = "${uiState.currency.sign}${totalAmount.toFormattedPrice(uiState.currency)}",
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
                .size(32.dp)
                .constrainAs(plusButton) {
                    top.linkTo(minusButton.top)
                    bottom.linkTo(minusButton.bottom)
                    start.linkTo(quantity.end, 10.dp)
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = false)
                ) {
                    quantityCount++
                },
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
        )

        MinniesDefaultButton(
            modifier = Modifier.constrainAs(addToCartButton) {
                top.linkTo(image.bottom, 10.dp)
                start.linkTo(image.start)
            }, text = "Add to cart",
            icon = Icons.Outlined.AddShoppingCart
        ) {
            addToCartAction(product.id, quantityCount, uiState.loggedInUser?.id)
            dismissModalAction()
        }

    }
}

@Preview
@Composable
fun PreviewAddToCartBottomSheet() {
    MinniesTheme {
        AddToCartBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            uiState = ProductViewModel.UiState.Success(
                ViewProduct(name = "Whatsapp Shirt"),
                Currency.EUR
            ), addToCartAction = { _, _, _ -> }, dismissModalAction = {}
        )
    }
}