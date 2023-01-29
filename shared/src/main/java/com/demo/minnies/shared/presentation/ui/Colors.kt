package com.demo.minnies.shared.presentation.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val eeirieBlack = Color(0xFF141414)
val keyLime = Color(0xFFECFFB0)
val pineTree = Color(0xFF1F2900)
val sheenGreen = Color(0xFFA8E000)
val blueYonder = Color(0xFF576CA8)
val wildBlueYonder = Color(0xFFA1ADCE)
val oxfordBlue = Color(0xFFA1ADCE)
val arcticLime = Color(0xFFD6FF5C)
val orangeRedCrayola = Color(0xFFFF5C5C)

val appColors =
    darkColorScheme(
        surface = eeirieBlack,
        background = eeirieBlack,
        onBackground = Color.White,
        onSurface = Color.White,
        primaryContainer = keyLime,
        secondaryContainer = keyLime,
        onPrimaryContainer = pineTree,
        onSecondaryContainer = pineTree,
        primary = sheenGreen,
        tertiary = blueYonder,
        onPrimary = Color.White,
        onTertiary = Color.White,
        tertiaryContainer = wildBlueYonder,
        onTertiaryContainer = blueYonder,
        secondary = arcticLime,
        onSecondary = Color.Black,
        scrim = pineTree.copy(alpha = 0.7F),
        onError = orangeRedCrayola
    )