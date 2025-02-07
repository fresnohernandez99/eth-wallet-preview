package com.gocash.wallet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
