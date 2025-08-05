package com.bbb.thecatapi

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bbb.thecatapi.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Gatitos",
    ) {
        initKoin()
        App()
    }
}