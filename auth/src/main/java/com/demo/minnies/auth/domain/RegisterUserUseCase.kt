package com.demo.minnies.auth.domain

import com.demo.minnies.auth.data.repos.AuthRepo
import com.demo.minnies.auth.data.repos.CacheRepo
import com.demo.minnies.shared.utils.validation.validateAsEmail
import com.demo.minnies.shared.utils.validation.validateAsName
import com.demo.minnies.shared.utils.validation.validateAsPassword
import com.demo.minnies.shared.utils.validation.validateAsPhoneNumber
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepo: AuthRepo,
    private val cacheRepo: CacheRepo
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String
    ) {

        check(email.validateAsEmail() == null) { "Please enter a valid email address." }
        check(fullName.validateAsName() == null) { "Please enter your full name." }
        check(phoneNumber.validateAsPhoneNumber() == null) { "Please enter a valid phone number." }


        val user = authRepo.register(
            emailAddress = email,
            password = password,
            fullName = fullName,
            phoneNumber = phoneNumber
        ).first()

        cacheRepo.storeLoggedInUser(checkNotNull(user) { "Something went wrong, could not complete the registration. Please try again." })
    }
}