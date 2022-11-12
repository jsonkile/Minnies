package com.demo.minnies.shared.utils


fun ByteArray.customString(): String {
    return String(android.util.Base64.encode(this, android.util.Base64.DEFAULT))
}

fun String.customByteArray(): ByteArray {
    return android.util.Base64.decode(this, android.util.Base64.DEFAULT)
}