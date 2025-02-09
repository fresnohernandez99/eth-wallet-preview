package com.gocash.wallet.ui.navigation

import com.gocash.wallet.util.Base64Util

object NavLinks {
    const val HOME = "home"
    const val REGISTER = "register"
    const val EXISTING_ACCOUNT = "existing_account"
    const val GENERATING = "generating/{params}"

    fun encode(value: String): String {
        return Base64Util.encodedMessage(value)
    }

    fun decode(value: String): String {
        val result = Base64Util.decodedMessage(value) ?: ""
        return result
    }

    fun goGenerating(params: String) =
        "generating/${encode(params)}"

}