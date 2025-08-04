package com.bbb.thecatapi.ui.home.tabs.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen() {
    val viewModel = koinViewModel<FavoritesViewModel>()
    Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {}
}