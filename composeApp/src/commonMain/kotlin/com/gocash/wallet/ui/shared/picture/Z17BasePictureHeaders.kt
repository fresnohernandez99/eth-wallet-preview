package com.gocash.wallet.ui.shared.picture

import coil3.annotation.ExperimentalCoilApi
import coil3.network.NetworkHeaders
import com.gocash.wallet.di.z17Singledi.SingletonInitializer

@OptIn(ExperimentalCoilApi::class)
class Z17BasePictureHeaders(private val headers: Map<String, String>? = null) {

    companion object : SingletonInitializer<Z17BasePictureHeaders>("base pictures") {
        fun fromMapToHeaders(map: Map<String, String>?): NetworkHeaders? {
            map?.let {
                val builder = NetworkHeaders.Builder()

                it.forEach { (key, value) ->
                    builder.add(key, value)
                }

                return builder.build()
            }
            return null
        }
    }

    private var _h: NetworkHeaders? = null

    fun getHeaders() = this._h

    fun updateHeaders(newHeaders: Map<String, String>) {
        this._h = fromMapToHeaders(newHeaders)
    }

    fun thereAreHeaders() = this._h != null

    init {
        headers?.let {
            updateHeaders(it)
        }
    }
}