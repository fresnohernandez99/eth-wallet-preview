package com.gocash.wallet.util

import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString

/**
 * The type Encode decode base 64.
 */
object Base64Util {
    /**
     * Decode encoded message using Base64
     *
     * @param message the message
     * @return the string
     */
    fun decodedMessage(message: String?): String? {
        return try {
            return if (message?.startsWith("{") == true) null
            else message!!.decodeBase64()?.utf8()//message!!.decodeFromBase64().decodeToString()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Encode message using Base64 encoding
     *
     * @param message the message
     * @return the string
     */
    fun encodedMessage(message: String): String {
        return message.encodeUtf8().base64().trim().replace("\n", "")
            .replace("/", "%_%")//message.toByteArray().encodeToBase64().trim().replace("\n", "")
    }

    fun encodedMessage(message: ByteArray): String {
        return message.toByteString().base64().trim().replace("\n", "")
            .replace("%_%", "/")//message.encodeToBase64().trim().replace("\n", "")
    }
}