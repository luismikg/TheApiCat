package com.bbb.thecatapi

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.bbb.thecatapi.ui.core.navigation.NavigationApp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationApp()
    }
}