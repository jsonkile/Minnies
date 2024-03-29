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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.minnies.shared.presentation.components.MinniesDefaultButton
import com.demo.minnies.shared.presentation.components.ErrorBar
import com.demo.minnies.shared.presentation.components.MinniesOutlinedTextField
import com.demo.minnies.shared.presentation.components.PageHeader
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.validation.*

const val REGISTER_ERROR_BAR_TEST_TAG = "REGISTER_ERROR_BAR_TEST_TAG"
const val FULL_NAME_TEXT_FIELD_TEST_TAG = "FULL_NAME_TEXT_FIELD_TEST_TAG"
const val EMAIL_TEXT_FIELD_TEST_TAG = "EMAIL_TEXT_FIELD_TEST_TAG"
const val PHONE_TEXT_FIELD_TEST_TAG = "PHONE_TEXT_FIELD_TEST_TAG"
const val PASSWORD_TEXT_FIELD_TEST_TAG = "PASSWORD_TEXT_FIELD_TEST_TAG"
const val CONFIRM_PASSWORD_TEXT_FIELD_TEST_TAG = "CONFIRM_PASSWORD_TEXT_FIELD_TEST_TAG"
const val REGISTER_BUTTON_TEST_TAG = "REGISTER_BUTTON_TEST_TAG"
const val REGISTER_SCREEN_CONTAINER_TEST_TAG = "REGISTER_SCREEN_CONTAINER_TEST_TAG"

@Composable
fun Register(gotoLoginScreen: () -> Unit, goBack: () -> Unit) {

    val viewModel = hiltViewModel<RegisterViewModel>()

    val uiState = viewModel.uiState.collectAsState().value

    if (uiState is RegisterViewModel.UiState.Success) goBack()

    RegisterScreen(uiState) { fullName, phoneNumber, emailAddress, password ->
        viewModel.register(
            fullName = fullName,
            phoneNumber = phoneNumber,
            emailAddress = emailAddress,
            password = password
        )
    }
}

@Composable
fun RegisterScreen(
    uiState: RegisterViewModel.UiState,
    register: (String, String, String, String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN)
            .testTag(REGISTER_SCREEN_CONTAINER_TEST_TAG)
    ) {

        PageHeader(
            heading = "Sign up",
            subHeading = "You are one step away from shopping bliss.",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    top = 20.dp,
                    bottom = if (uiState !is RegisterViewModel.UiState.Error) 30.dp else 0.dp
                )
        )

        if (uiState is RegisterViewModel.UiState.Error) {
            ErrorBar(
                message = uiState.throwable.message.orEmpty(),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 10.dp)
                    .testTag(REGISTER_ERROR_BAR_TEST_TAG)
            )
        }

        var fullName by remember { mutableStateOf("") }
        val fullNameError = fullName.validateAsName()

        MinniesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(FULL_NAME_TEXT_FIELD_TEST_TAG),
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
            },
            error = fullNameError
        )


        Spacer(modifier = Modifier.height(20.dp))

        var emailAddress by remember { mutableStateOf("") }
        val emailAddressError = emailAddress.validateAsEmail()

        MinniesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(EMAIL_TEXT_FIELD_TEST_TAG),
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

        var phoneNumber by remember { mutableStateOf("") }
        val phoneNumberError = phoneNumber.validateAsPhoneNumber()

        MinniesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(PHONE_TEXT_FIELD_TEST_TAG),
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
            },
            error = phoneNumberError
        )


        Spacer(modifier = Modifier.height(20.dp))

        var password by remember { mutableStateOf("") }
        val passwordError = password.validateAsPassword()

        MinniesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(PASSWORD_TEXT_FIELD_TEST_TAG),
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
            },
            error = passwordError
        )


        Spacer(modifier = Modifier.height(20.dp))

        var confirmPassword by remember { mutableStateOf("") }
        val confirmPasswordError = password.validateAsConfirmPassword(password)

        MinniesOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .testTag(CONFIRM_PASSWORD_TEXT_FIELD_TEST_TAG),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    modifier = Modifier,
                    text = "Confirm password",
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
            value = confirmPassword,
            onValueChanged = {
                confirmPassword = it
            },
            error = confirmPasswordError
        )

        Spacer(modifier = Modifier.height(30.dp))


        MinniesDefaultButton(
            modifier = Modifier.testTag(REGISTER_BUTTON_TEST_TAG),
            text = when (uiState) {
                is RegisterViewModel.UiState.Loading -> LOADING_TEXT
                else -> "Create my account"
            },
            enabled = uiState !is RegisterViewModel.UiState.Success,
            isLoading = uiState is RegisterViewModel.UiState.Loading
        ) {
            register(fullName, phoneNumber, emailAddress, password)
        }


    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    MinniesTheme {
        RegisterScreen(RegisterViewModel.UiState.Default) { _, _, _, _ ->

        }
    }
}