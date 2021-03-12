package com.maciel.murillo.musales.di

import com.maciel.murillo.musales.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    factory { DeleteAdUseCase(repository = get()) }
    factory { GetAdUseCase(repository = get()) }
    factory { GetAllAdsUseCase(repository = get()) }
    factory { IsUserLoggedUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
    factory { LogoutUseCase(repository = get()) }
    factory { RegisterAdUseCase(repository = get()) }
    factory { SignupUseCase(repository = get()) }
    factory { UpdateAdUseCase(repository = get()) }
}