package com.demo.minnies.shared.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CurrencyExchange
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.demo.minnies.shared.presentation.ui.MinniesTheme
import com.demo.minnies.shared.presentation.ui.PAGE_HORIZONTAL_MARGIN
import com.demo.minnies.shared.utils.Currency

@Composable
fun ActionBox(actionBox: ActionBox, modifier: Modifier) {

    ConstraintLayout(
        modifier = modifier
            .clickable { actionBox.action() }
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 10.dp)
    ) {

        val (title, subtitle, icon) = createRefs()

        Image(
            imageVector = actionBox.icon,
            contentDescription = "${actionBox.title} icon",
            modifier = Modifier
                .size(30.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Fit
        )


        Text(
            text = actionBox.title,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(icon.end, 15.dp)
                    top.linkTo(icon.top)
                    bottom.linkTo(subtitle.top, 2.dp)
                },
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ), color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = actionBox.subtitle,
            modifier = Modifier
                .constrainAs(subtitle) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom, 2.dp)
                    bottom.linkTo(icon.bottom)
                },
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            ), color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7F)
        )

    }

}

@Preview
@Composable
fun PreviewActionBox() {
    MinniesTheme {
        ActionBox(
            ActionBox(
                title = "Logout",
                subtitle = "Leave the app",
                icon = Icons.Outlined.Logout
            ) {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

data class ActionBox(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color = Color.Green,
    val iconBackgroundColor: Color = Color.Green,
    val action: () -> Unit
)


@Composable
fun SwitchBox(switchBox: SwitchBox, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 10.dp)
    ) {

        val (title, subtitle, icon, directionArrow) = createRefs()

        Image(
            imageVector = switchBox.icon,
            contentDescription = "${switchBox.title} icon",
            modifier = Modifier
                .size(30.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Fit
        )


        Text(
            text = switchBox.title,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(icon.end, 15.dp)
                    top.linkTo(icon.top)
                    bottom.linkTo(subtitle.top, 2.dp)
                },
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ), color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = switchBox.subtitle,
            modifier = Modifier
                .constrainAs(subtitle) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom, 2.dp)
                    bottom.linkTo(icon.bottom)
                },
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            ), color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7F)
        )


        Checkbox(
            modifier = Modifier
                .size(15.dp)
                .constrainAs(directionArrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            checked = switchBox.checked,
            onCheckedChange = {
                switchBox.action(it)
            }
        )

    }
}

@Preview
@Composable
fun PreviewSwitchBox() {
    MinniesTheme {
        SwitchBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            switchBox = SwitchBox(
                title = "Notifications",
                subtitle = "Turn on/off app notifications",
                action = {},
                checked = true,
                icon = Icons.Outlined.Notifications
            )
        )
    }
}

data class SwitchBox(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color = Color.Green,
    val iconBackgroundColor: Color = Color.Green,
    val checked: Boolean = false,
    val action: (Boolean) -> Unit
)


@Composable
fun OptionsBox(optionsBox: OptionsBox, modifier: Modifier) {

    var popUpState by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
            .clickable { popUpState = true }
            .padding(horizontal = PAGE_HORIZONTAL_MARGIN, vertical = 10.dp)
    ) {

        val (title, subtitle, icon, directionArrow) = createRefs()

        Image(
            imageVector = optionsBox.icon,
            contentDescription = "${optionsBox.title} icon",
            modifier = Modifier
                .size(30.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Fit
        )


        Text(
            text = optionsBox.title,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(icon.end, 15.dp)
                    top.linkTo(icon.top)
                    bottom.linkTo(subtitle.top, 2.dp)
                },
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ), color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = optionsBox.subtitle,
            modifier = Modifier
                .constrainAs(subtitle) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom, 2.dp)
                    bottom.linkTo(icon.bottom)
                },
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            ), color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7F)
        )


        Text(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(directionArrow) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = optionsBox.selectedValue,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (popUpState) {
            Popup(
                alignment = Alignment.BottomEnd,
                onDismissRequest = { popUpState = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    optionsBox.options.forEach { option ->
                        Text(
                            text = option,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .clickable {
                                    optionsBox.optionClickAction(option)
                                    popUpState = false
                                }
                        )
                    }
                }
            }
        }


    }
}

@Preview
@Composable
fun PreviewOptionsBox() {
    MinniesTheme {
        OptionsBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            optionsBox = OptionsBox(
                title = "Currency",
                subtitle = "Choose how you want to see prices",
                optionClickAction = {},
                icon = Icons.Outlined.CurrencyExchange
            )
        )
    }
}

data class OptionsBox(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color = Color.Green,
    val iconBackgroundColor: Color = Color.Green,
    val options: List<String> = emptyList(),
    val selectedValue: String = Currency.USD.name,
    val optionClickAction: (String) -> Unit
)
