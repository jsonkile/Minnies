package com.demo.minnies.shared.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


fun ByteArray.customString(): String {
    return String(android.util.Base64.encode(this, android.util.Base64.DEFAULT))
}

fun String.customByteArray(): ByteArray {
    return android.util.Base64.decode(this, android.util.Base64.DEFAULT)
}

fun Double.toFormattedPrice(currency: Currency): String {
    val formatter = DecimalFormat("#,###.##")

    return formatter.format(
        BigDecimal(this * currency.perDollar).setScale(2, RoundingMode.HALF_DOWN)
    )
}

fun Double.toFormattedPriceWithSign(currency: Currency): String {
    val formatter = DecimalFormat("#,###.##")

    return "${currency.sign}${
        formatter.format(
            BigDecimal(this * currency.perDollar).setScale(
                2,
                RoundingMode.HALF_DOWN
            )
        )
    }"
}