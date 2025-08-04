package com.bbb.thecatapi.di

import com.bbb.thecatapi.ui.home.tabs.favorites.FavoritesViewModel
import com.bbb.thecatapi.ui.home.tabs.online.OnlineViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {

    viewModelOf(::OnlineViewModel)
    viewModelOf(::FavoritesViewModel)
}
