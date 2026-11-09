package dev.eagelware.phonex.di

interface PlatformComponent {
    fun getInfo(): String
}

expect fun platformModule(): Module