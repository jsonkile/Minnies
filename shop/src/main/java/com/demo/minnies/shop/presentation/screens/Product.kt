package com.demo.minnies.shop.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shop.presentation.models.ViewProduct


@Composable
fun Product(viewModel: ProductViewModel = hiltViewModel()) {
    ProductScreen(ViewProduct())
}


@Composable
fun ProductScreen(viewProduct: ViewProduct) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewProduct.image)
                .crossfade(true)
                .build(),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(color = Color.DarkGray)

        )
    }
}


@Preview
@Composable
fun PreviewProductScreen() {
    MinniesTheme {
        ProductScreen(ViewProduct())
    }
}