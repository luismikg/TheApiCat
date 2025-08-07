package com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.cash.paging.compose.LazyPagingItems
import com.bbb.thecatapi.domain.model.BreedsModel
import com.bbb.thecatapi.domain.model.FavoriteModel
import com.bbb.thecatapi.ui.home.tabs.favorites.FavoritesScreen
import com.bbb.thecatapi.ui.home.tabs.online.OnlineScreen

@Composable
fun NavigationBottom(
    list: LazyPagingItems<BreedsModel>,
    listFavorites: State<List<FavoriteModel>>,
    refresh: () -> Unit,
    navigator: NavHostController,
    showDarkBackgroundLoading: (Boolean) -> Unit,
    showDarkBackground: (Boolean) -> Unit,
) {

    NavHost(navController = navigator, startDestination = BottomNavigationItem.Online.route) {
        composable(route = BottomNavigationItem.Online.route) {

            OnlineScreen(
                list = list,
                listFavorites = listFavorites,
                refresh = refresh,
                showDarkBackgroundLoading = showDarkBackgroundLoading,
                showDarkBackground = showDarkBackground
            )
        }
        composable(route = BottomNavigationItem.Favorite.route) {
            FavoritesScreen(listFavorites = listFavorites)
        }
    }
}
