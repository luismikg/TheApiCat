package com.bbb.thecatapi.core.composableLibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bbb.thecatapi.getColorTheme

@Composable
fun LoadingScreen() {
    val colors = getColorTheme()

    Box(
        modifier = Modifier
            .fillMaxSize().background(colors.fieldInactive.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}