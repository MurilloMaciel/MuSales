package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository

class RegisterAdUseCase(private val repository: Repository) {

    suspend operator fun invoke(ad: Ad) = repository.registerAd(ad)
}