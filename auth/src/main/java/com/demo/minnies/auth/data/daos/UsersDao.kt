package com.demo.minnies.auth.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("select full_name, phone_number, email_address from users where email_address = :emailAddress limit 1")
    fun getUserByEmail(emailAddress: String): Flow<PartialUser?>

    @Query("select full_name, phone_number, email_address from users where email_address = :emailAddress and password = :password limit 1")
    fun getUserByEmailAndPassword(emailAddress: String, password: String): Flow<PartialUser?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User): Long

    @Update(entity = User::class)
    fun updateUser(partialUser: PartialUser): Int

    @Delete(entity = User::class)
    fun deleteUser(partialUser: PartialUser): Int
}