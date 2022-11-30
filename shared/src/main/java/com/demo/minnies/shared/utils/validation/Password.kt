package com.demo.minnies.shared.utils.validation

import com.demo.minnies.shared.utils.Error
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

fun String.validateAsPassword(): Error? {
    var error: Error? = null
    validator()
        .atleastOneNumber()
        .atleastOneLowerCase()
        .atleastOneUpperCase()
        .atleastOneSpecialCharacters()
        .nonEmpty()
        .addErrorCallback {
            error = Error(it)
        }.check()
    return error
}