package com.demo.minnies.shared.utils.validation

import com.demo.minnies.shared.utils.Error
import com.wajahatkarim3.easyvalidation.core.Validator
import com.wajahatkarim3.easyvalidation.core.collection_ktx.noSpecialCharactersList
import com.wajahatkarim3.easyvalidation.core.rules.BaseRule
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

fun String.validateAsName(): Error? {
    val trimmedString = this.trim()
    var error: Error? = null
    trimmedString.validator()
        .contains(" ", "Please enter your full name.")
        .noNumbers()
        .nonEmpty()
        .addRule(CustomNoSpecialCharacterRule())
        .minLength(5)
        .addErrorCallback {
            error = Error(it)
        }.check()
    return error
}


/**
 * Rule allows spaces
 */
class CustomNoSpecialCharacterRule(var errorMsg: String = "Should not contain any special characters") :
    BaseRule {
    override fun validate(text: String): Boolean {
        if (text.isEmpty())
            return false

        return Validator(text).regex("[A-Za-z0-9 ]+").check()
    }

    override fun getErrorMessage(): String = errorMsg

    override fun setError(msg: String) {
        errorMsg = msg
    }
}