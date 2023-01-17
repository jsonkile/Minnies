package com.demo.minnies.auth.presentation.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateShippingAddress(
    currentAddress: String,
    updateAction: (String) -> Unit,
    closeAction: () -> Unit,
    loading: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 10.dp)
    ) {


        Text(
            text = "Update Shipping address",
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 17.sp, fontWeight = FontWeight.SemiBold,
            )
        )

        var address by remember { mutableStateOf(currentAddress) }

        OutlinedTextField(
            keyboardOptions = KeyboardOptions.Default,
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 15.dp),
            singleLine = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            )
        )

        MinniesDefaultButton(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp),
            text = "Submit",
            enabled = address.isNotEmpty(),
            isLoading = loading
        ) {
            updateAction(address)
            closeAction()
        }
    }
}

@Preview
@Composable
fun PreviewUpdateShippingAddress() {
    MinniesTheme {
        UpdateShippingAddress(
            currentAddress = "Immigration Office",
            updateAction = {},
            closeAction = {},
            loading = false
        )
    }
}