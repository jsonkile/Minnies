package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepoRoomImpl @Inject constructor(
    private val dao: UsersDao
) : UserRepo {

    override fun getUser(emailAddress: String) = dao.getUserByEmail(emailAddress)

    override fun getUser(id: Long): Flow<PartialUser?> = dao.getUserById(id)

    override fun getUser(emailAddress: String, password: String): Flow<PartialUser?> =
        dao.getUserByEmailAndPassword(emailAddress, password)

    override fun addUser(user: User) = dao.addUser(user)

    override fun removeUser(partialUser: PartialUser) = dao.deleteUser(partialUser)

    override fun updateUser(partialUser: PartialUser) = dao.updateUser(partialUser)
}