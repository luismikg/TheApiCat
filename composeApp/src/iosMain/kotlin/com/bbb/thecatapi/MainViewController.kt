package com.bbb.thecatapi

import androidx.compose.ui.window.ComposeUIViewController
import com.bbb.thecatapi.di.initKoin
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        // Lee la clave del Info.plist
        val apiKey = NSBundle.mainBundle.infoDictionary?.get("TheCatApiKey") as? String
            ?: error("API Key no encontrada en Info.plist")

        initKoin(apiKey = apiKey)
        App()
    }
}
