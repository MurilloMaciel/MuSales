package com.maciel.murillo.musales.di

import com.maciel.murillo.musales.presentation.MainViewModel
import com.maciel.murillo.musales.presentation.ads.AdsViewModel
import com.maciel.murillo.musales.presentation.auth.AuthViewModel
import com.maciel.murillo.musales.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MainViewModel()
    }

    viewModel {
        SplashViewModel(
            isUserLoggedUseCase = get(),
        )
    }

    viewModel {
        AuthViewModel(
            signupUseCase = get(),
            loginUseCase = get(),
        )
    }

    viewModel {
        AdsViewModel(
//            signupUseCase = get(),
        )
    }
}