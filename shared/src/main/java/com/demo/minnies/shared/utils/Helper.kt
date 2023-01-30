package com.demo.minnies.shared.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import org.apache.commons.lang3.RandomStringUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}


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

fun getRandomString(length: Int): String = RandomStringUtils.randomNumeric(length)

fun getDateTime(timestamp: Long): String {
    return try {
        val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy, HH:mm", Locale.ENGLISH)
        simpleDateFormat.format(Date(timestamp))
    } catch (e: Exception) {
        "-"
    }
}