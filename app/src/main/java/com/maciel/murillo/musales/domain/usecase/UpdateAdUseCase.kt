package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository

class UpdateAdUseCase(private val repository: Repository) {

    suspend operator fun invoke(ad: Ad) = repository.updateAd(ad)
}