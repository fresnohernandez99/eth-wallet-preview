package com.gocash.wallet.ui.navigation

import com.gocash.wallet.util.Base64Util

object NavLinks {
    const val HOME = "home"
    const val REGISTER = "register"
    const val EXISTING_ACCOUNT = "existing_account"
    const val CHAT_P2P = "chat_p2p/{params}"

    fun encode(value: String): String {
        return Base64Util.encodedMessage(value)
    }

    fun decode(value: String): String {
        val result = Base64Util.decodedMessage(value) ?: ""
        return result
    }
//
//    private fun goChatP2P(params: String) =
//        "chat_p2p/${encode(params)}"
//
//    private fun goChatGroup(params: String) =
//        "chat_group/${encode(params)}"
}