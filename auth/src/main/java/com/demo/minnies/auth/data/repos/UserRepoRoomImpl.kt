package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.room.models.PartialUser
import com.demo.minnies.database.room.models.ShippingAddress
import com.demo.minnies.database.room.models.User
import com.demo.minnies.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepoRoomImpl @Inject constructor(
    private val dao: UsersDao, @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserRepo {

    override fun getUser(emailAddress: String) = dao.getUserByEmail(emailAddress)

    override fun getUser(id: Long): Flow<PartialUser?> = dao.getUserById(id)

    override fun getUser(emailAddress: String, password: String): Flow<PartialUser?> =
        dao.getUserByEmailAndPassword(emailAddress, password)

    override fun peekPassword(emailAddress: String): String? = dao.getPasswordByEmail(emailAddress)

    override fun addUser(user: User) = dao.addUser(user)

    override fun removeUser(partialUser: PartialUser) = dao.deleteUser(partialUser)

    override fun updateUser(partialUser: PartialUser) = dao.updateUser(partialUser)

    override suspend fun updateUserShippingAddress(shippingAddress: ShippingAddress) =
        withContext(dispatcher) {
            dao.updateShippingAddress(shippingAddress)
            dao.getUserById(shippingAddress.id).first()
        }
}