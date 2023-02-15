package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.auth.data.repos.UserRepo
import com.demo.minnies.database.models.User
import com.demo.minnies.shared.utils.EMAIL_TAKEN_MESSAGE
import com.demo.minnies.shared.utils.GENERIC_ERROR_MESSAGE
import com.demo.minnies.shared.utils.validation.validateAsEmail
import com.demo.minnies.shared.utils.validation.validateAsName
import com.demo.minnies.shared.utils.validation.validateAsPhoneNumber
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface RegisterUserUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    )
}

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepo: UserRepo,
    private val cacheRepo: CacheRepo
) : RegisterUserUseCase {
    override suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {

        check(email.validateAsEmail() == null) { "Please enter a valid email address." }
        check(fullName.validateAsName() == null) { "Please enter your full name." }
        check(phoneNumber.validateAsPhoneNumber() == null) { "Please enter a valid phone number." }

        check(userRepo.getUser(email).first() == null) { EMAIL_TAKEN_MESSAGE }

        val newUser = userRepo.getUser(
            userRepo.addUser(
                User(
                    emailAddress = email,
                    password = password,
                    fullName = fullName,
                    phoneNumber = phoneNumber
                )
            )
        ).first()

        checkNotNull(newUser) { GENERIC_ERROR_MESSAGE }

        cacheRepo.storeLoggedInUser(newUser)
    }
}