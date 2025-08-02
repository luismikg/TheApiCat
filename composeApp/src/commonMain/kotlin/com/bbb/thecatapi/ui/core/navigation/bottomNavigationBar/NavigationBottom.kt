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
    /*listState: LazyListState,
    onClickItemLoan: (FinancialLoanModelUI) -> Unit,
    listSavingMoney: List<FinancialSavingMoneyModelUI> = emptyList(),
    listLoan: List<FinancialLoanModelUI> = emptyList()*/
) {

    NavHost(navController = navigator, startDestination = BottomNavigationItem.Online.route) {
        composable(route = BottomNavigationItem.Online.route) {
            /*LoanScreen(listLoan, listState = listState) { itemLoan ->
                onClickItemLoan(itemLoan)
            }*/
            OnlineScreen()
        }
        composable(route = BottomNavigationItem.Favorite.route) {
            //SavingMoneyScreen(listSavingMoney)
            FavoritesScreen()
        }
    }
}
