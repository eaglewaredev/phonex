package dev.eagelware.phonex

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.eagelware.phonex.di.initKoin

fun main() = application {
    initKoin()
//    initKoin {
//        printLogger()
//    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "PhoneX",
    ) {

        App()
    }
}