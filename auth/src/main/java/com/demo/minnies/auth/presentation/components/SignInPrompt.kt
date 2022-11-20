package com.demo.minnies.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.minnies.shared.BuildConfig
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN

const val SIGN_IN_PROMPT_TEST_TAG = "SIGN_IN_PROMPT_TEST_TAG"
const val SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG = "SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG"
const val SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG = "SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG"

@Composable
fun SignInPrompt(signUpButtonClickAction: () -> Unit, loginButtonClickAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                shape = androidx.compose.ui.graphics.RectangleShape,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            .testTag(SIGN_IN_PROMPT_TEST_TAG)
    ) {
        Text(
            text = "${BuildConfig.GRADLE_APP_NAME} is much better when we know who you are",
            modifier = Modifier.padding(
                start = PAGE_HORIZONTAL_MARGIN,
                end = PAGE_HORIZONTAL_MARGIN,
                bottom = 15.dp,
                top = 20.dp
            ),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondaryContainer)
        )

        Row(
            modifier = Modifier.padding(
                start = PAGE_HORIZONTAL_MARGIN, bottom = 15.dp, end = PAGE_HORIZONTAL_MARGIN
            ), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .testTag(SIGN_IN_PROMPT_CREATE_ACCOUNT_BUTTON_TAG),
                onClick = { signUpButtonClickAction() }) {
                Text(
                    text = "Create an account",
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }

            OutlinedButton(modifier = Modifier
                .wrapContentSize()
                .testTag(SIGN_IN_PROMPT__LOGIN_BUTTON_TEST_TAG),
                onClick = { loginButtonClickAction() }) {
                Text(
                    text = "Login", modifier = Modifier.padding(bottom = 5.dp), style = TextStyle(
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewSignInPrompt() {
    MinniesTheme {
        SignInPrompt({}, {})
    }
}