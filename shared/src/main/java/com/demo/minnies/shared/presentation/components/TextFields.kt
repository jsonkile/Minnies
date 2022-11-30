package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.demo.minnies.shared.utils.Error

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    keyboardOptions: KeyboardOptions,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = MaterialTheme.colorScheme.primary,
        containerColor = Color.Transparent,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
    ),
    shape: CornerBasedShape = RoundedCornerShape(5.dp),
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    value: String = "",
    error: Error? = null,
    onValueChanged: (String) -> Unit
) {

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it)
            },
            modifier = modifier,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            label = { label() },
            leadingIcon = { leadingIcon() },
            colors = colors,
            shape = shape,
            isError = error != null && value.isNotEmpty(),
            visualTransformation = visualTransformation
        )

        if (error != null && value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = error.errorMessage,
                modifier = Modifier,
                style = TextStyle(color = MaterialTheme.colorScheme.error)
            )
        }
    }

}


