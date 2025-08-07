package com.bbb.thecatapi.ui.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bbb.thecatapi.ui.home.HomeScreen
import com.bbb.thecatapi.ui.login.LoginScreen

@Composable
fun NavigationApp() {
    val mainController = rememberNavController()

    NavHost(navController = mainController, startDestination = Routes.LoginScreen.route) {
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(
                onNextScreen = { mainController.navigate(Routes.HomeScreen.route) },
                onClickForgotPassword = {},
                onClickRegister = {}
            )
        }

        composable(route = Routes.HomeScreen.route) {
            HomeScreen(
                onClickExit = { mainController.navigateUp() },
                onNextScreen = { mainController.navigate(Routes.DetailScreen.route) }
            )
        }

        composable(route = Routes.DetailScreen.route) {
            Column { }
        }
    }
}