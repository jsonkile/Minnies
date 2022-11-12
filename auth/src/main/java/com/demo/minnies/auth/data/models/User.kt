package com.demo.minnies.auth.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    val password: String //AES Encrypted
)

/**
 * Partial User Entity
 */
@kotlinx.serialization.Serializable
data class PartialUser(
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "email_address")
    val emailAddress: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String
)
