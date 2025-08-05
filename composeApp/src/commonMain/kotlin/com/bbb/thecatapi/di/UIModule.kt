package com.bbb.thecatapi.di

import com.bbb.thecatapi.ui.home.tabs.favorites.FavoritesViewModel
import com.bbb.thecatapi.ui.home.tabs.online.OnlineViewModel
import com.bbb.thecatapi.ui.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {

    viewModelOf(::OnlineViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::LoginViewModel)
}
