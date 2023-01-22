package com.demo.minnies.database.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.ShippingAddress
import com.demo.minnies.database.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("select id, full_name, email_address, phone_number, shipping_address from users where email_address = :emailAddress limit 1")
    fun getUserByEmail(emailAddress: String): Flow<PartialUser?>

    @Query("select id, full_name, email_address, phone_number, shipping_address from users where id = :id")
    fun getUserById(id: Long): Flow<PartialUser?>

    @Query("select id, full_name, email_address, phone_number, shipping_address from users where email_address = :emailAddress and password = :password limit 1")
    fun getUserByEmailAndPassword(emailAddress: String, password: String): Flow<PartialUser?>

    @Query("select password from users where email_address = :emailAddress")
    fun getPasswordByEmail(emailAddress: String): String?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(user: User): Long

    @Update(entity = User::class)
    fun updateUser(partialUser: PartialUser): Int

    @Update(entity = User::class)
    fun updateShippingAddress(shippingAddress: ShippingAddress): Int

    @Delete(entity = User::class)
    fun deleteUser(partialUser: PartialUser): Int
}