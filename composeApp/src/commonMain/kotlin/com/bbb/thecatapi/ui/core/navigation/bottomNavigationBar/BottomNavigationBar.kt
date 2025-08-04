package com.bbb.thecatapi.ui.core.navigation.bottomNavigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bbb.thecatapi.getColorTheme

@Composable
fun BottomNavigationBar(
    bottomNavigationItem: List<BottomNavigationItem>,
    navController: NavHostController,
    currentScreen: (String) -> Unit
) {
    val colors = getColorTheme()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = colors.backgroundColor
    ) {
        bottomNavigationItem.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = "") },
                label = { Text(text = item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    currentScreen(item.route)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.textMainColor,
                    selectedIndicatorColor = colors.primaryAccent,
                    unselectedIconColor = colors.primary,
                    unselectedTextColor = colors.textMainColor,
                    disabledIconColor = colors.textSecondaryColor,
                    disabledTextColor = colors.textSecondaryColor
                )
            )
        }
    }
}