package com.demo.minnies.auth.data.repos

import com.demo.minnies.auth.data.daos.UsersDao
import com.demo.minnies.auth.data.models.PartialUser
import com.demo.minnies.auth.data.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepoRoomImpl @Inject constructor(
    private val dao: UsersDao
) : UserRepo {

    override fun getUser(emailAddress: String) = dao.getUserByEmail(emailAddress)

    override fun getUser(emailAddress: String, password: String): Flow<PartialUser?> =
        dao.getUserByEmailAndPassword(emailAddress, password)

    override fun addUser(user: User) = dao.addUser(user)

    override fun removeUser(partialUser: PartialUser) = dao.deleteUser(partialUser)

    override fun updateUser(partialUser: PartialUser) = dao.updateUser(partialUser)
}