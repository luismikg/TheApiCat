package com.bbb.thecatapi.di

import com.bbb.thecatapi.domain.GetCatBreedsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetCatBreedsUseCase)
}