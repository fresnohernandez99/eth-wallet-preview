package com.gocash.wallet.di.z17Singledi

import kotlin.concurrent.Volatile

open class SingletonInitializer<T : Any>(
    private val className: String
) {
    @Volatile
    private var instance: T? = null

    fun createInstance(creator: () -> T): T {
        return instance ?: creator().also { instance = it }
    }

    @Throws(SinglediException::class)
    fun getInstance(): T {
        if (instance == null) throw SinglediException(className)
        return instance!!
    }

    fun getInstanceOrNull(): T? {
        return instance
    }

    fun deleteInstance() {
        instance = null
    }
}