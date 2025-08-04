package com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bbb.thecatapi.ui.home.tabs.favorites.FavoritesScreen
import com.bbb.thecatapi.ui.home.tabs.online.OnlineScreen

@Composable
fun NavigationBottom(
    navigator: NavHostController,
    showDarkBackground: (Boolean) -> Unit,
) {

    NavHost(navController = navigator, startDestination = BottomNavigationItem.Online.route) {
        composable(route = BottomNavigationItem.Online.route) {

            OnlineScreen(showDarkBackground = showDarkBackground)
        }
        composable(route = BottomNavigationItem.Favorite.route) {
            FavoritesScreen()
        }
    }
}
