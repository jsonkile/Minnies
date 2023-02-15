package com.demo.minnies.auth.data.repos

import com.demo.minnies.database.room.daos.UsersDao
import com.demo.minnies.database.models.PartialUser
import com.demo.minnies.database.models.ShippingAddress
import com.demo.minnies.database.models.User
import com.demo.minnies.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepoRoomImpl @Inject constructor(
    private val dao: UsersDao, @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserRepo {

    override fun getUser(emailAddress: String) = dao.getUserByEmail(emailAddress).flowOn(dispatcher)

    override fun getUser(id: Long): Flow<PartialUser?> = dao.getUserById(id).flowOn(dispatcher)

    override fun getUser(emailAddress: String, password: String): Flow<PartialUser?> =
        dao.getUserByEmailAndPassword(emailAddress, password).flowOn(dispatcher)

    override suspend fun peekPassword(emailAddress: String): String? =
        withContext(dispatcher) { dao.getPasswordByEmail(emailAddress) }

    override suspend fun addUser(user: User) = withContext(dispatcher) { dao.addUser(user) }

    override suspend fun removeUser(partialUser: PartialUser) =
        withContext(dispatcher) { dao.deleteUser(partialUser) }

    override suspend fun updateUser(partialUser: PartialUser) =
        withContext(dispatcher) { dao.updateUser(partialUser) }

    override suspend fun updateUserShippingAddress(shippingAddress: ShippingAddress) =
        withContext(dispatcher) {
            dao.updateShippingAddress(shippingAddress)
            dao.getUserById(shippingAddress.id).first()
        }
}