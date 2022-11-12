package com.demo.minnies.auth.utils

sealed class CustomExceptions(override val message: String) : Throwable(message = message) {

    class BadRequestException(override val message: String) : CustomExceptions(message)

    class NotFoundException(override val message: String) : CustomExceptions(message)

    class InternalErrorException(override val message: String) : CustomExceptions(message)

}