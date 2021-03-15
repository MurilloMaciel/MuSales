package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class ReadUserIdUseCase(private val repository: Repository) {

    suspend operator fun invoke() = repository.readUserUid()
}