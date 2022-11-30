package com.demo.minnies.shared.utils.validation

import com.demo.minnies.shared.utils.Error
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail

fun String.validateAsEmail(): Error? {
    var error: Error? = null
    validEmail(callback = {
        error = Error(it)
    }, errorMsg = "The email address is not valid.")
    return error
}