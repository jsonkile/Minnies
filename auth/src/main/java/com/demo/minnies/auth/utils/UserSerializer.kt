package com.demo.minnies.auth.utils

import androidx.datastore.core.Serializer
import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<PartialUser?> {

    override val defaultValue: PartialUser?
        get() = null

    override suspend fun readFrom(input: InputStream): PartialUser? {
        return try {
            Json.decodeFromString(PartialUser.serializer(), input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: PartialUser?, output: OutputStream) {
        t?.let {
            output.write(Json.encodeToString(PartialUser.serializer(), t).encodeToByteArray())
        }
    }
}