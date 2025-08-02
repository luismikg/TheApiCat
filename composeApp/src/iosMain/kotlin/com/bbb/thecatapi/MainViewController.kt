package com.bbb.thecatapi

import androidx.compose.ui.window.ComposeUIViewController
import com.bbb.thecatapi.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }