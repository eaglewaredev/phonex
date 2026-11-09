package dev.eagelware.phonex.di

import android.content.Context
import android.os.Build
import dev.eagelware.phonex.Platform

class AndroidComponent(private val context: Context) : PlatformComponent {
    override fun getInfo() = "Android ${context.packageName}"
}

actual fun platformModule(): Module = module {
    single<AndroidComponent>() bind PlatformComponent::class
}