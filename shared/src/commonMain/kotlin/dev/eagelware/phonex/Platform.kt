package dev.eagelware.phonex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform