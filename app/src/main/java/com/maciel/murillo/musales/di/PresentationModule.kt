package com.maciel.murillo.musales.di

import com.maciel.murillo.musales.presentation.ad_details.AdDetailViewModel
import com.maciel.murillo.musales.presentation.ads.AdsViewModel
import com.maciel.murillo.musales.presentation.auth.AuthViewModel
import com.maciel.murillo.musales.presentation.my_ads.MyAdsViewModel
import com.maciel.murillo.musales.presentation.register_ad.RegisterAdViewModel
import com.maciel.murillo.musales.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SplashViewModel()
    }

    viewModel {
        AuthViewModel(
            signupUseCase = get(),
            loginUseCase = get(),
        )
    }

    viewModel {
        AdsViewModel(
            isUserLoggedUseCase = get(),
            getAllAdsUseCase = get(),
            getAdsFilteredUseCase = get(),
            logoutUseCase = get(),
        )
    }

    viewModel {
        MyAdsViewModel(
            getMyAdsUseCase = get(),
            readUserIdUseCase = get(),
            deleteAdUseCase = get()
        )
    }

    viewModel {
        RegisterAdViewModel(
            getUserIdUseCase = get(),
            registerAdUseCase = get(),
            saveImagesUseCase = get(),
            updateAdUseCase = get(),
        )
    }

    viewModel {
        AdDetailViewModel()
    }
}