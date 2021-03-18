package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository

class SaveImageUseCase(private val repository: Repository) {

    suspend operator fun invoke() = repository.saveImage()
}