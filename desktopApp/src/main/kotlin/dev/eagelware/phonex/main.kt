package dev.eagelware.phonex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.eagelware.phonex.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PhoneX",
    ) {
        initKoin {
            printLogger()
        }
        App()
    }
}