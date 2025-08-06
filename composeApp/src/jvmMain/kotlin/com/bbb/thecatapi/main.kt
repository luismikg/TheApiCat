package com.bbb.thecatapi

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.bbb.thecatapi.di.initKoin
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Gatitos",
    ) {

        val apiKey = BuildKonfig.THE_CAT_API_KEY
        val window = this.window
        LaunchedEffect(window) {
            val minWidthPx = 400
            val minHeightPx = 1000
            window.minimumSize = Dimension(minWidthPx, minHeightPx)
        }

        initKoin(apiKey = apiKey)
        App()
    }
}