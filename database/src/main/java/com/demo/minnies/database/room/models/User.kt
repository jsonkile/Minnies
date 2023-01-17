package com.demo.minnies.database.room.models

import androidx.datastore.core.Serializer
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "shipping_address")
    val shippingAddress: String = "",
    val password: String //AES Encrypted
)

/**
 * Partial User Entity
 */
@kotlinx.serialization.Serializable
data class PartialUser(
    val id: Long = 0,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "shipping_address")
    val shippingAddress: String = ""
)


/**
 * Shipping Address Update
 */
data class ShippingAddress(
    val id: Long = 0,
    @ColumnInfo(name = "shipping_address")
    val shippingAddress: String
)

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