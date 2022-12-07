package com.demo.minnies.shared.utils.validation

import com.demo.minnies.shared.utils.Error
import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.rules.BaseRule
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validNumber
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

fun String.validateAsPhoneNumber(): Error? {
    var error: Error? = null
    validator()
        .nonEmpty()
        .minLength(8)
        .validNumber("Please enter a valid phone number.")
        .addErrorCallback {
            error = Error(it)
        }.check()
    return error
}

/**
 * Rule for phone numbers
 */
class CustomPhoneRule(var errorMsg: String = "Phone number is not valid.") : BaseRule {
    override fun validate(text: String): Boolean {
        if (text.isEmpty()) return false

        return Validator(text).regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$")
            .check()
    }

    override fun getErrorMessage(): String = errorMsg

    override fun setError(msg: String) {
        errorMsg = msg
    }
}