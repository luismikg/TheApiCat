package com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.bbb.thecatapi.ui.core.navigation.Routes

sealed class BottomNavigationItem(
    val icon: ImageVector,
    val title: String,//StringResource,
    val route: String
) {
    data object Online : BottomNavigationItem(
        icon = Icons.Default.Book,
        title = "Gatos",
        route = Routes.OnlineScreen.route
    )

    data object Favorite : BottomNavigationItem(
        icon = Icons.Default.Favorite,
        title = "Favoritos",
        route = Routes.FavoritesScreen.route
    )
}