package com.demo.minnies.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.auth.presentation.AuthScreen
import com.demo.minnies.shared.presentation.components.DefaultButton
import com.demo.minnies.shared.presentation.components.OutlinedTextField
import com.demo.minnies.shared.presentation.components.PageHeader
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN


@Composable
fun CreateAccount(toLoginScreen: () -> Unit) {

    val viewModel = hiltViewModel<CreateAccountViewModel>()

    CreateAccountScreen()
}

@Composable
fun CreateAccountScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
    ) {

        PageHeader(
            heading = "Sign up",
            subHeading = "You are one step away from shopping bliss.",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, bottom = 30.dp)
        )

        var fullName by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            label = {
                Text(
                    modifier = Modifier,
                    text = "Full name",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
            },
            leadingIcon = {
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "full name",
                    colorFilter = ColorFilter.tint(color = Color.LightGray)
                )
            },
            value = fullName,
            onValueChanged = {
                fullName = it
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        var emailAddress by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next
            ),
            label = {
                Text(
                    modifier = Modifier,
                    text = "Email address",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
            },
            leadingIcon = {
                Image(
                    imageVector = Icons.Rounded.Email,
                    contentDescription = "email address",
                    colorFilter = ColorFilter.tint(color = Color.LightGray)
                )
            },
            value = emailAddress,
            onValueChanged = {
                emailAddress = it
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        var phoneNumber by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Phone,
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next
            ),
            label = {
                Text(
                    modifier = Modifier,
                    text = "Phone number",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
            },
            leadingIcon = {
                Image(
                    imageVector = Icons.Rounded.Phone,
                    contentDescription = "phone",
                    colorFilter = ColorFilter.tint(color = Color.LightGray)
                )
            },
            value = phoneNumber,
            onValueChanged = {
                phoneNumber = it
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    modifier = Modifier,
                    text = "Password",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                )
            },
            leadingIcon = {
                Image(
                    imageVector = Icons.Outlined.Password, contentDescription = "password",
                    colorFilter = ColorFilter.tint(color = Color.LightGray)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChanged = {
                password = it
            }
        )


        Spacer(modifier = Modifier.height(30.dp))


        DefaultButton(modifier = Modifier, text = "Create my account") {

        }


    }
}

@Preview
@Composable
fun PreviewCreateAccountScreen() {
    MinniesTheme {
        CreateAccountScreen()
    }
}