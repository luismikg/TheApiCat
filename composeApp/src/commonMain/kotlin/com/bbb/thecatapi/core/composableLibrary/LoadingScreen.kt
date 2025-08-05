package com.bbb.thecatapi.core.composableLibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.bbb.thecatapi.getColorTheme

@Composable
fun LoadingScreen(
    showLoading: Boolean
) {
    val colors = getColorTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.fieldInactive.copy(alpha = 0.5f))
            .clickable {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.alpha(if (showLoading) 1f else 0f)
        )
    }
}