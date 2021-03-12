package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class LoginUseCase(private val repository: Repository) {

    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)
}