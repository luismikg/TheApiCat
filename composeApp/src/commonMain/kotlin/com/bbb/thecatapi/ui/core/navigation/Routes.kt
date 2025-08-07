package com.bbb.thecatapi.ui.core.navigation

sealed class Routes(val route: String) {
    data object LoginScreen : Routes("LoginScreen")
    data object HomeScreen : Routes("HomeScreen")
    data object DetailScreen : Routes("DetailScreen")

    data object OnlineScreen : Routes("CatsScreen")
    data object FavoritesScreen : Routes("FavoritesCatsScreen")
}