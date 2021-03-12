package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class GetAdUseCase(private val repository: Repository) {

    suspend operator fun invoke(id: String) = repository.getAd(id)
}