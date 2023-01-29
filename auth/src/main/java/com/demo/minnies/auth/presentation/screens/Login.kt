package com.demo.minnies.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.components.ErrorBar
import com.demo.minnies.shared.presentation.components.PageHeader
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.AuthScreen
import com.demo.minnies.shared.utils.validation.validateAsEmail
import com.demo.minnies.shared.utils.validation.validateAsPassword

const val LOGIN_ERROR_BAR_TEST_TAG = "LOGIN_ERROR_BAR_TEST_TAG"
const val LOADING_TEXT = "Loading..."

@Composable
fun Login(gotoRegisterScreen: () -> Unit, goBack: () -> Unit) {

    val viewModel = hiltViewModel<LoginViewModel>()

    val uiState = viewModel.uiState.collectAsState().value

    LoginScreen(uiState = uiState, login = { email, password ->
        viewModel.login(email, password)
    })


    LaunchedEffect(uiState) {
        if (uiState is LoginViewModel.UiState.Success) goBack()
    }
}

@Composable
fun LoginScreen(uiState: LoginViewModel.UiState, login: (String, String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
    ) {

        PageHeader(
            heading = AuthScreen.Login.name,
            subHeading = "We have missed you, please enter your details.",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    top = 20.dp,
                    bottom = if (uiState !is LoginViewModel.UiState.Error) 30.dp else 0.dp
                )
        )


        if (uiState is LoginViewModel.UiState.Error) {
            ErrorBar(
                message = uiState.throwable.message.orEmpty(),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 10.dp)
                    .testTag(LOGIN_ERROR_BAR_TEST_TAG)
            )
        }


        var emailAddress by remember { mutableStateOf("") }
        val emailAddressError = emailAddress.validateAsEmail()

        com.demo.minnies.shared.presentation.components.MinniesOutlinedTextField(
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
            },
            error = emailAddressError
        )


        Spacer(modifier = Modifier.height(20.dp))


        var password by remember { mutableStateOf("") }
        val passwordError = password.validateAsPassword()

        com.demo.minnies.shared.presentation.components.MinniesOutlinedTextField(
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
                    imageVector = Icons.Outlined.Password,
                    contentDescription = "password",
                    colorFilter = ColorFilter.tint(color = Color.LightGray)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChanged = {
                password = it
            },
            error = passwordError
        )


        Spacer(modifier = Modifier.height(30.dp))

        MinniesDefaultButton(
            modifier = Modifier,
            text = when (uiState) {
                is LoginViewModel.UiState.Loading -> LOADING_TEXT
                else -> "Let me in"
            },
            isLoading = uiState is LoginViewModel.UiState.Loading,
            enabled = uiState !is LoginViewModel.UiState.Success
        ) {
            if (emailAddressError == null && passwordError == null && uiState !is LoginViewModel.UiState.Loading)
                login(emailAddress, password)
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    MinniesTheme {
        LoginScreen(LoginViewModel.UiState.Default) { _, _ -> }
    }
}